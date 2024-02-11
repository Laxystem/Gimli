/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.networking.webfinger

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.ImmutableMap
import quest.laxla.gimli.util.emptyPersistentList
import quest.laxla.gimli.util.emptyPersistentMap

@Serializable
data class JsonResourceDescriptor(
    val subject: String,
    val aliases: ImmutableList<String> = emptyPersistentList(),
    val properties: ImmutableMap<String, String> = emptyPersistentMap(),
    val links: ImmutableList<JrdLink> = emptyPersistentList()
)
