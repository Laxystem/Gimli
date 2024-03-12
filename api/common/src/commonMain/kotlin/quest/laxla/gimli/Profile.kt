/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package quest.laxla.gimli

import com.eygraber.uri.Uri
import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.emptyString


/**
 * Something that can be the author of [Post]s.
 */
@Specification.Compliance(Specification.ActivityPub, name = "Actor")
public interface Profile : Element<Profile> {
    public val voter: Voter

    public val topic: Topic

    @Specification.Compliance(Specification.ActivityPub, name = "name")
    public val displayName: String

    @Specification.Compliance(Specification.ActivityPub, name = "summary")
    public val about: String

    @Specification.Compliance(Specification.ActivityPub, name = "preferredUsername")
    public val preferredUsername: String

    @Serializable
    public data class CreateBuilder(
        val owner: Ref<Voter>,
        val displayName: String = emptyString(),
        val title: String = emptyString(),
        val about: String = emptyString()
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    public data class UpdateBuilder(
        override val primaryFederalIdentifier: Uri,
        val displayName: String? = null,
        val title: String? = null,
        val about: String? = null
    ): Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
