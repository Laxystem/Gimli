/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import kotlinx.coroutines.flow.*

/**
 * [Extension], etc. manager.
 *
 * @since 0.0.1-alpha.3
 */
public interface Instance {
    /**
     * The current [Status] of this instance.
     *
     * @since 0.0.1-alpha.3
     */
    public val status: Status

    /**
     * Fetches all [Extension]s currently loaded to this instance, as a [Flow].
     *
     * @since 0.0.1-alpha.3
     */
    public suspend fun asFlow(): Flow<Extension>

    /**
     * Retrieves the [Extension] with this [namespace][Extension.namespace], if loaded.
     *
     * @since 0.0.1-alpha.3
     */
    public suspend fun get(extensionNamespace: String): Extension?

    /**
     * Retrieves the [Extension] with this [namespace][Extension.namespace].
     *
     * @since 0.0.1-alpha.3
     */
    @Throws(ExtensionNotLoadedException::class)
    public suspend fun getOrThrow(extensionNamespace: String): Extension

    /**
     * Is [extension] loaded?
     *
     * @since 0.0.1-alpha.3
     */
    public suspend infix fun contains(extension: Extension): Boolean

    /**
     * Is an [Extension] of this [namespace][Extension.namespace] loaded?
     *
     * @since 0.0.1-alpha.3
     */
    public suspend infix fun contains(extensionNamespace: String): Boolean

    /**
     * Unloads all extensions, then cancels all their coroutines.
     *
     * @param safe should exceptions in [Extension] unloading prevent the instance from shutting down?
     * @since 0.0.1-alpha.3
     */
    public suspend fun shutdown(safe: Boolean = true)

    /**
     * Represents the ability of an [Extension] to manipulate [instance].
     *
     * The extension itself is private,
     * as scopes are intended to be used only as a parameter to event functions.
     *
     * @since 0.0.1-alpha.3
     * @see Scope
     * @see MutationScope
     */
    public interface AbstractScope<T> where T : AbstractScope<T> {
        /**
         * The instance this scope's [Extension] can manipulate.
         *
         * @since 0.0.1-alpha.3
         */
        public val instance: Instance

        @Deprecated(message = "Currently only exists for overridability, as API design is still WIP.", level = DeprecationLevel.HIDDEN)
        @get:Throws(UnsupportedOperationException::class)
        public val extension: Extension
            get() = throw UnsupportedOperationException("The [quest.laxla.gimli.Instance.AbstractScope.extension] property is not considered public API, and may be removed in the future. This implementation does not support it.")

        /**
         * Does this scope belong to this [extension]?
         *
         * @since 0.0.1-alpha.3
         */
        public infix fun isOf(extension: Extension): Boolean

        /**
         * Does this scope belong to an extension with this [namespace][Extension.namespace]?
         *
         * @since 0.0.1-alpha.3
         */
        public infix fun isOf(extensionNamespace: String): Boolean
    }

    /**
     * Represents the ability of an [Extension] to immutably manipulate [instance].
     *
     * @since 0.0.1-alpha.3
     * @see AbstractScope
     * @see MutationScope
     */
    public interface Scope : AbstractScope<Scope>

    /**
     * Represents the ability of an [Extension] to manipulate and mutate [instance].
     *
     * Currently, this interface functions exactly the same as [Scope].
     * See [Gimli#9](https://codeberg.org/Moria/Gimli/issues/9).
     *
     * @since 0.0.1-alpha.3
     * @see AbstractScope
     * @see Scope
     */
    public interface MutationScope : AbstractScope<MutationScope>

    /**
     * Represents the current status of an [Instance].
     */
    public enum class Status(public val acceptsMutations: Boolean = true) {
        /**
         * The [Instance] is currently running, nothing special to report.
         */
        Running,

        /**
         * The [Instance] is currently loading or unloading [Extension]s.
         *
         * Mutations will be suspended until the instance finishes executing the current mutation.
         */
        Mutating,

        /**
         * The instance is offline, and waits for garbage collection to pick it up.
         */
        Offline(acceptsMutations = false);
    }

    /**
     * @since 0.0.1-alpha.3
     */
    public companion object {
        /**
         * Fetches all [Extension]s of type [T] currently loaded to this instance.
         */
        public suspend inline fun <reified T> Instance.extensions(): Flow<T> where T : Extension =
            asFlow().filterIsInstance<T>()

        /**
         * Fetches the [Extension] of type [T] currently loaded to this instance, if any.
         */
        public suspend inline fun <reified T> Instance.extensionOrNull(): T? where T : Extension =
            asFlow().filterIsInstance<T>().singleOrNull()

        /**
         * Fetches the [Extension] of type [T] currently loaded to this instance.
         *
         * @throws IllegalStateException if more than one extension of type [T] is loaded.
         * @throws NoSuchElementException if an extension of type [T] isn't loaded.
         */
        @Throws(IllegalStateException::class, NoSuchElementException::class)
        public suspend inline fun <reified T> Instance.extension(): T where T: Extension = extensions<T>().single()
    }
}
