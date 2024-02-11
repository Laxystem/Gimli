/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.util

import kotlin.jvm.JvmInline

/**
 * An [Optional] supporting nullable types.
 */
public sealed interface Optional<out T> {
    @JvmInline
    public value class Present<T>(public val value: T) : Optional<T>
    public data object Empty : Optional<Nothing>

    public companion object {
        /**
         * Wraps [value] with an [Optional].
         */
        public infix fun <T> of(value: T): Optional<T> = Present(value)

        /**
         * Wraps this with an [Optional].
         */
        public fun <T> T.asOptional(): Optional<T> = Optional of this

        /**
         * Wraps this with an [Optional], returning [Empty] if `null`.
         */
        public fun <T> T?.asOptionalOrEmpty(): Optional<T> where T : Any = if (this == null) Empty else Optional of this

        /**
         * Returns the value of [Optional], or throws.
         */
        public val <T> Optional<T>.value: T
            get() = if (this is Present<T>) value else throw IllegalStateException("Optional is empty")

        /**
         * Returns the value of [Optional], or `null` if [Empty].
         */
        public val <T> Optional<T>.valueOrNull: T? where T : Any
            get() = if (this is Present<T>) value else null
    }
}
