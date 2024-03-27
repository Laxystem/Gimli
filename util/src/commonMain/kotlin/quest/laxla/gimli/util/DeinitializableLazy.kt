/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

import kotlin.reflect.KProperty

/**
 * Represents a value with lazy initialization that can be [deinitialize]d.
 *
 * To create an instance of [DeinitializableLazy] use the [deinitializableLazy] function.
 *
 * Doesn't extend [Lazy], as [Lazy.isInitialized]'s contract specifies it mustn't return `false` after returning `true`.
 */
public interface DeinitializableLazy<out T> {
    public val value: T
    public val isInitialized: Boolean

    public fun deinitialize()
}

public expect fun <T> deinitializableLazy(initializer: () -> T): DeinitializableLazy<T>

public expect fun <T> deinitializableLazy(isSynchronized: Boolean, initializer: () -> T): DeinitializableLazy<T>

/**
 * An extension to delegate a read-only property of type [T] to an instance of [DeinitializableLazy].
 *
 * ```kotlin
 * val property: String by lazy { initializer }
 * ```
 */
public operator fun <T> DeinitializableLazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

internal object Uninitialized

internal class UnsafeLazyImpl<out T>(private val initializer: () -> T) : DeinitializableLazy<T> {
    private var _value: Any? = Uninitialized

    override val value: T
        get() {
            if (_value === Uninitialized) {
                _value = initializer()
            }

            @Suppress("UNCHECKED_CAST")
            return _value as T
        }

    override val isInitialized: Boolean
        get() = _value !== Uninitialized

    override fun deinitialize() {
        _value = Uninitialized
    }
}
