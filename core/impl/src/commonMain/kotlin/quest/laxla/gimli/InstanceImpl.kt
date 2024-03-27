/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import quest.laxla.gimli.util.OnlyCallableBy

@OptIn(OnlyCallableBy::class)
internal class InstanceImpl private constructor(name: String, extensions: Iterable<Extension>) : Instance {
    private lateinit var _isOffline: Unit
    private val mutex = Mutex(locked = true)
    private val coroutineScope: CoroutineScope =
        CoroutineScope(context = Dispatchers.Default + CoroutineName(name) + SupervisorJob())

    override val status: Instance.Status
        get() = when {
            ::_isOffline.isInitialized -> Instance.Status.Offline
            mutex.isLocked -> Instance.Status.Mutating
            else -> Instance.Status.Running
        }

    @Deprecated(message = "Should not be used directly, replace with `scopeOf`, `scopeOfOrNull` and `contains` if possible.")
    private val extensions = mutableMapOf<String, ScopeImpl>()


    init {
        coroutineScope.launch {
            extensions.forEach { extension -> // TODO: sort by dependence
                if (!shouldLoad(extension, isDynamicallyTriggered = false)) return@forEach

                @Suppress("DEPRECATION")
                this@InstanceImpl.extensions[extension.namespace] = ScopeImpl(extension)

                afterLoading(extension, isDynamicallyTriggered = false)
            }

            mutex.unlock()
        }
    }

    private abstract inner class AbstractScope<T>(@Suppress("OVERRIDE_DEPRECATION") final override val extension: Extension) :
        Instance.AbstractScope<T> where T : Instance.AbstractScope<T> {

        final override val instance: Instance
            get() = this@InstanceImpl

        protected abstract fun scopeOf(extension: Extension): T
        abstract val coroutineScope: CoroutineScope

        final override fun isOf(extension: Extension): Boolean = extension === this.extension

        final override fun isOf(extensionNamespace: String): Boolean = extensionNamespace == extension.namespace

        final override fun toString(): String = "[${extension.namespace}] at $instance"

        override fun equals(other: Any?): Boolean = when {
            this === other -> true
            other == null || other !is AbstractScope<*> || this::class != other::class -> false

            extension !== other.extension -> false
            instance !== other.instance -> false

            else -> true
        }

        override fun hashCode(): Int = instance.hashCode() * 31 + extension.hashCode()
    }

    private inner class ScopeImpl(extension: Extension) : AbstractScope<Instance.Scope>(extension), Instance.Scope {
        val mutationScope = Mutable(extension)

        override val coroutineScope: CoroutineScope = CoroutineScope(
            context = Dispatchers.Default
                    + CoroutineName(extension.namespace)
                    + SupervisorJob(this@InstanceImpl.coroutineScope.coroutineContext.job)
        )

        @Suppress("DEPRECATION")
        override fun scopeOf(extension: Extension): ScopeImpl = extensions[extension.namespace]
            ?: throw ConcurrentModificationException("Extension [${extension.namespace}] was unloaded during event propagation.")

        private inner class Mutable(extension: Extension) : AbstractScope<Instance.MutationScope>(extension),
            Instance.MutationScope {
            override val coroutineScope: CoroutineScope
                get() = this@ScopeImpl.coroutineScope

            override fun scopeOf(extension: Extension): Mutable = this@ScopeImpl.scopeOf(extension).mutationScope
        }
    }

    @Suppress("DEPRECATION")
    override suspend fun asFlow(): Flow<Extension> = extensions.values.asFlow().map { it.extension }

    @Suppress("DEPRECATION")
    override suspend fun contains(extensionNamespace: String): Boolean = extensionNamespace in extensions

    @Suppress("DEPRECATION")
    override suspend fun contains(extension: Extension): Boolean =
        extensions[extension.namespace]?.extension === extension

    @Suppress("DEPRECATION")
    private fun scopeOfOrNull(extensionNamespace: String): ScopeImpl? = extensions[extensionNamespace]?.also {
        if (extensionNamespace != it.extension.namespace)
            throw IllegalStateException("Extension [$extensionNamespace] violates contract: Namespace mustn't be mutable.")
    }

    override suspend fun get(extensionNamespace: String): Extension? = scopeOfOrNull(extensionNamespace)?.extension

    private fun scopeOf(extensionNamespace: String): ScopeImpl = scopeOfOrNull(extensionNamespace)
        ?: throw ExtensionNotLoadedException("Extension [$extensionNamespace] is not loaded.")

    override suspend fun getOrThrow(extensionNamespace: String): Extension = scopeOf(extensionNamespace).extension

    private suspend fun shouldLoad(extension: Extension, isDynamicallyTriggered: Boolean): Boolean {
        if (status === Instance.Status.Offline) throw UnsupportedOperationException("Instance is offline - cannot load extensions, [${extension.namespace}] included.")
        if (this contains extension) return false
        if (this contains extension.namespace) throw IncompatibleExtensionSetException(
            extensionNamespace = extension.namespace,
            message = "Extension conflict: this extension is already loaded."
        )

        // TODO: dependency check

        val beforeLoad = coroutineScope.async {
            try {
                extension.beforeLoad(isDynamicallyTriggered = true, instance = this@InstanceImpl)
            } catch (e: Throwable) {
                throw IncompatibleExtensionSetException(
                    cause = e,
                    extensionNamespace = extension.namespace,
                    message = "Loading failed."
                )
            }
        }

        @Suppress("DEPRECATION")
        awaitAll(*extensions.values.map { dependency -> // TODO: actually filter by dependencies
            dependency.coroutineScope.async {
                try {
                    dependency.extension.beforeDependentLoad(extension, isDynamicallyTriggered, scope = dependency)
                } catch (e: Throwable) {
                    if (e !is CancellationException) throw IncompatibleExtensionSetException(
                        cause = e,
                        extensionNamespace = extension.namespace,
                        raisingExtensionNamespace = dependency.extension.namespace,
                        message = "Loading failed, extension isn't supported by its dependency."
                    )
                }
            }
        }.toTypedArray(), beforeLoad)

        return true
    }

    private suspend fun afterLoading(extension: Extension, isDynamicallyTriggered: Boolean) {
        val extensionScope = scopeOf(extension.namespace)

        extensionScope.coroutineScope.launch {
            @OptIn(LockingApi::class)
            extension.onLoad(isDynamicallyTriggered, scope = extensionScope)
        }.join()

        extensionScope.coroutineScope.launch {
            extension.afterLoad(isDynamicallyTriggered, scope = extensionScope.mutationScope)
        }

        @Suppress("DEPRECATION")
        for (dependency in extensions.values) dependency.coroutineScope.launch { // TODO: actually filter by dependencies
            dependency.extension.afterDependentLoad(extension, isDynamicallyTriggered, scope = dependency.mutationScope)
        }
    }

    override suspend fun shutdown(safe: Boolean) = TODO("Implement shutting instance down")

    companion object {
        suspend operator fun invoke(name: String, extensions: Iterable<Extension>): Instance {
            val instance = InstanceImpl(name, extensions)
            instance.mutex.withLock(owner = this) { } // wait until initialization is finished and mutex is unlocked
            return instance
        }
    }
}
