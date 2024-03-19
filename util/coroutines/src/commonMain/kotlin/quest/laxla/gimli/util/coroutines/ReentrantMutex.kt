/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util.coroutines

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

public suspend inline fun <T> Mutex.withReentrantLock(crossinline block: suspend (mutexKey: ReentrantMutexContextElement) -> T): T {
    val key = ReentrantMutexContextKey(mutex = this)
    val element = coroutineContext[key]

    return if (element == null) {
        val newElement = ReentrantMutexContextElement(key)

        withContext(newElement) {
            withLock { block(newElement) }
        }
    } else block(element)
}

public class ReentrantMutexContextElement(
    override val key: ReentrantMutexContextKey
) : CoroutineContext.Element

public data class ReentrantMutexContextKey(
    val mutex: Mutex
) : CoroutineContext.Key<ReentrantMutexContextElement>
