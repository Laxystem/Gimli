/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli

/**
 * An [Account], [Profile] or anything else capable using [Authorizable]s' [abilities][Ability].
 */
public interface Accessor : Element.Federalized<Accessor> {
    /**
     * The [Profile] whose [accessor][Profile.accessor] is this one, if existing and known.
     *
     * Implementing this property isn't required.
     */
    public val associatedProfile: Profile? get() = null

    /**
     * The name this [Accessor] should appear as,
     * if it does not have an [associatedProfile] or if the client prefers not to use it.
     */
    public val displayName: String get() = primaryFederalIdentifier
}
