/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package quest.laxla.gimli

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.emptyString


/**
 * Something that can be the author of [Post]s.
 */
@Specification.Compliance(Specification.ActivityPub, name = "Actor")
public sealed interface Profile : Element.Federalized<Profile> {
    @Specification.Compliance(Specification.Gimli, name = "accessor")
    public val accessor: Accessor

    @Specification.Compliance(Specification.Gimli, name = "authorizable")
    public val authorizable: Authorizable

    @Specification.Compliance(Specification.ActivityPub, name = "name")
    public val displayName: String

    @Specification.Compliance(Specification.Gimli, name = "shortSummary")
    public val title: String

    @Specification.Compliance(Specification.ActivityPub, name = "summary")
    public val about: String

    @Specification.Compliance(Specification.ActivityPub, name = "preferredUsername")
    public val preferredUsername: String

    @Serializable
    public data class CreateBuilder(
        val owner: Ref<Accessor>,
        var displayName: String = emptyString(),
        var title: String = emptyString(),
        var about: String = emptyString()
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    public data class UpdateBuilder(
        override val primaryFederalIdentifier: String,
        var displayName: String? = null,
        var title: String? = null,
        var about: String? = null
    ): Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
