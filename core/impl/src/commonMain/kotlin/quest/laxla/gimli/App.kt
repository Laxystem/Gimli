/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import kotlinx.coroutines.CoroutineScope

/**
 * Let main be concurrent!
 */
internal expect fun execMain(block: suspend CoroutineScope.() -> Unit)

public fun main(vararg params: String): Unit = execMain {
    InstanceImpl(name = params.firstOrNull() ?: "Gimli Instance ${hashCode()}", extensions)
}
