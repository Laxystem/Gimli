/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import quest.laxla.gimli.util.OnlyCallableBy

/**
 * Stateless-ly extends functionality of an [Instance] or of another extension.
 *
 * @since 0.0.1-alpha.3
 */
public interface Extension {
    /**
     * The computer-readable name of this extension.
     * Usually equals to its [qualified name][kotlin.reflect.KClass.qualifiedName].
     *
     * @since 0.0.1-alpha.3
     */
    public val namespace: String

    /**
     * Executed before this extension is loaded.
     *
     * If an [IncompatibleExtensionSetException] is thrown, this extension won't be loaded.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    @Throws(IncompatibleExtensionSetException::class)
    public fun beforeLoad(isDynamicallyTriggered: Boolean, instance: Instance) {
    }

    /**
     * Executed when this extension is loaded.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    @LockingApi
    public suspend fun onLoad(isDynamicallyTriggered: Boolean, scope: Instance.Scope) {
    }

    /**
     * Executed after this extension is loaded,
     * at the same time this extension's dependencies execute their [afterDependentLoad].
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    public suspend fun afterLoad(isDynamicallyTriggered: Boolean, scope: Instance.MutationScope) {
    }

    /**
     * Executed before a dependent extension loads.
     *
     * If an [IncompatibleExtensionSetException] is thrown, the dependent extension won't be loaded.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    @Throws(IncompatibleExtensionSetException::class, UnsupportedDynamicallyException::class)
    public suspend fun beforeDependentLoad(
        dependent: Extension,
        isDynamicallyTriggered: Boolean,
        scope: Instance.Scope
    ) {
    }

    /**
     * Executed before a dependent extension unloads.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    public suspend fun afterDependentLoad(
        dependent: Extension,
        isDynamicallyTriggered: Boolean,
        scope: Instance.MutationScope
    ) {
    }

    /**
     * Executed before a dependent extension [unloads][onUnload], and after [beforeUnload].
     *
     * If an [IncompatibleExtensionSetException] is thrown, the dependent exception won't be unloaded.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    @Throws(IncompatibleExtensionSetException::class, UnsupportedDynamicallyException::class)
    public suspend fun beforeDependentUnload(
        dependent: Extension,
        isDynamicallyTriggered: Boolean,
        scope: Instance.Scope
    ) {
    }

    /**
     * Executed after a dependent extension is unloaded.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    public suspend fun afterDependentUnload(
        dependent: Extension,
        isDynamicallyTriggered: Boolean,
        scope: Instance.MutationScope
    ) {
    }

    /**
     * Executed when the instance is about to shut down, before extensions are unloaded.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    public suspend fun beforeShutdown(scope: Instance.Scope) {
    }

    /**
     * Executed before this extension is unloaded.
     *
     * If the instance is shutting down (= [isDynamicallyTriggered] is false), executed after [beforeShutdown].
     * Otherwise, if an [IncompatibleExtensionSetException] is thrown, the unloading will be cancelled.
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    @Throws(IncompatibleExtensionSetException::class)
    public suspend fun beforeUnload(isDynamicallyTriggered: Boolean, scope: Instance.Scope) {
    }

    /**
     * Executed before this extension is unloaded, and after its dependencies have been [notified][beforeDependentUnload].
     *
     * After the execution of this function,
     * all of this extension's [children][kotlinx.coroutines.Job.children]s will be [cancelled][kotlinx.coroutines.Job.cancel].
     *
     * @since 0.0.1-alpha.3
     */
    @OnlyCallableBy(Instance::class)
    public suspend fun onUnload(isDynamicallyTriggered: Boolean, scope: Instance.Scope) {
    }
}
