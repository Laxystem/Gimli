/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package quest.laxla.gimli

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

public interface Guild : Element<Guild> {
    /**
     * This Guild's anonymous posting [Profile].
     */
    @Specification.Compliance(Specification.ActivityPub, name = "actor")
    public val profile: Ref<Profile>

    /**
     * The default [Railway] permissions.
     *
     * Guilds also have additional, unique permissions that are stored in this property.
     * Permissions for the Guild's anonymous profile can be found at [profile].[authorizable][Profile.topic].
     */
    public val defaultRailwayPermissions: Ref<Topic>

    /**
     * The [Tag]s this Guild has claimed.
     */
    @Specification.Compliance(Specification.ActivityPub, name = "tag")
    public val tags: Flow<Tag> // TODO

    public fun claim(tag: Ref<Tag>) // TODO

    public suspend fun claimAndGet(tag: Ref<Tag>) // TODO

    @Serializable
    public data class CreateBuilder(val profile: Profile) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }
}
