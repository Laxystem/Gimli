/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

public actual fun <T> deinitializableLazy(
    isSynchronized: Boolean,
    initializer: () -> T
): DeinitializableLazy<T> = if (isSynchronized) SynchronizedLazyImpl(initializer) else UnsafeLazyImpl(initializer)

public actual fun <T> deinitializableLazy(initializer: () -> T): DeinitializableLazy<T> = SynchronizedLazyImpl(initializer)

private class SynchronizedLazyImpl<out T>(private val initializer: () -> T, lock: Any? = null) : DeinitializableLazy<T> {
    @Volatile private var _value: Any? = Uninitialized
    private val lock = lock ?: this

    @Suppress("UNCHECKED_CAST")
    override val value: T
        get() = _value?.takeUnless { it === Uninitialized } as T? ?: synchronized(lock) {
            val value = _value

            if (value !== Uninitialized) value as T else initializer().also { _value = it }
        }

    override val isInitialized: Boolean
        get() = _value !== Uninitialized

    override fun deinitialize() = synchronized(lock) {
        _value = Uninitialized
    }
}
