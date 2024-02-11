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
import quest.laxla.gimli.util.Maybe
import quest.laxla.gimli.util.Optional
import quest.laxla.gimli.util.emptyString

public interface Railway {
    public val guild: Guild

    @Specification.Compliance(Specification.ActivityPub, name = "name")
    public val displayName: String

    public val startFromTop: Maybe

    @Specification.Compliance(Specification.ActivityPub, name = "summary")
    public val description: String

    @Serializable
    public data class CreateBuilder(
        val guild: Guild,
        var description: String = emptyString(),
        var displayName: String = emptyString(),
        var startFromTop: Boolean = false
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    @Serializable
    public data class UpdateBuilder(
        override val primaryFederalIdentifier: String,
        var description: Optional<String> = Optional.Empty,
        var displayName: Optional<String> = Optional.Empty,
        var startFromTop: Optional<Boolean> = Optional.Empty
    ) : Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = copy()
    }
}
