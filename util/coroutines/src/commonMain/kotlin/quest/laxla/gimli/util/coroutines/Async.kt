/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util.coroutines

import kotlinx.coroutines.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalContracts::class)
public inline fun CoroutineScope.asyncVoidingCancellation(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: suspend CoroutineScope.() -> Unit
): Deferred<Unit> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return async(context, start) {
        try {
            block()
        } catch (_: CancellationException) { }
    }
}
