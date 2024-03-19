/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package quest.laxla.gimli.social

/**
 * An [Account], [Profile] or anything else capable using [Topic]s' [abilities][Ability].
 */
@Specification.Compliance(Specification.Fep5a4f, name = "Voter")
public interface Voter : Element<Voter> {
    /**
     * The [Profile] whose [accessor][Profile.voter] is this one, if existing and known.
     *
     * Implementing this property isn't required.
     */
    @Specification.Compliance(Specification.ActivityPub, name = "actor")
    public val associatedProfile: Profile? get() = null

    /**
     * The name this [Voter] should appear as,
     * if it does not have an [associatedProfile] or if the client prefers not to use it.
     */
    @Specification.Compliance(Specification.ActivityPub, name = "name")
    public val displayName: String get() = primaryFederalIdentifier.toString()
}
