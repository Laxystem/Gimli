/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.ld.property

import kotlinx.serialization.SerialName
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.ld.LdType

interface LdpType {
    @SerialName("@id")
    val type: ImmutableList<LdType>
}