/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.networking.webfinger

import kotlinx.serialization.Serializable
import quest.laxla.gimli.server.networking.util.ContentType
import quest.laxla.gimli.util.IetfBcp47
import quest.laxla.gimli.util.ImmutableMap
import quest.laxla.gimli.util.emptyPersistentMap

@Serializable
data class JrdLink(
    val rel: String,
    val type: ContentType? = null,
    val href: String? = null,
    val titles: ImmutableMap<IetfBcp47, String> = emptyPersistentMap()
)
