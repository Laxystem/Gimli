/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import kotlinx.collections.immutable.ImmutableSet

public actual val extensions: ImmutableSet<Extension> by lazy(::discoverExtensions)

private fun discoverExtensions(): ImmutableSet<Extension> =
    throw NotImplementedError("Should've been replaced by the server!")
