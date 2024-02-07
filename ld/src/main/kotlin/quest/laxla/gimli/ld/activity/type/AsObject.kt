/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project: Gimli and contributors.
 */

package quest.laxla.gimli.ld.activity.type

import kotlinx.serialization.SerialName
import quest.laxla.gimli.ld.activity.ActivityStreams
import quest.laxla.gimli.ld.activity.Type
import quest.laxla.gimli.ld.activity.property.AspID
import quest.laxla.gimli.ld.activity.property.AspType
import quest.laxla.gimli.ld.activity.toActivityStreamsType

@SerialName(ActivityStreams + "Object")
interface AsObject : AspID, AspType {
    companion object : Type by "Object".toActivityStreamsType()
}
