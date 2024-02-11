/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.Optional

public interface Tag : Element<Tag> {
    public val creationTime: Instant
    public val guild: Ref<Guild>?
    public val name: String
}
