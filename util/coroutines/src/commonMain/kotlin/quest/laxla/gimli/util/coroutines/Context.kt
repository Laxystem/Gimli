/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util.coroutines

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

public operator fun CoroutineContext?.plus(other: CoroutineContext?): CoroutineContext = when {
    this == null -> other
    other == null -> this
    else -> this + other
} ?: EmptyCoroutineContext
