/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli

import quest.laxla.gimli.util.Percentage
import quest.laxla.gimli.util.Percentage.Companion.toPercentage
import kotlin.time.Duration

/**
 * Represents [Accessor]s' ability to use [permission] on the [authorizable].
 */
public interface Ability : Element<Ability> {
    /**
     * The [Authorizable] the [permission] is performed on.
     */
    public val authorizable: Authorizable

    /**
     * The [Permission] performed on the [authorizable].
     */
    public val permission: String

    /**
     * The length of [Vote]s invoking this [Ability].
     *
     * [Vote]s may treat this as a *default*
     * if [authorizable] allows [custom vote lengths][Authorizable.isAllowingCustomVoteLength].
     *
     * `null` means this ability uses the [default][Authorizable.defaultVoteLength].
     */
    public val voteLength: Duration?

    /**
     * The [minimum voter turnout][Authorizable.defaultMinimumVoterTurnout] of [Vote]s invoking this [Ability].
     *
     * `null` means this ability uses the [default][Authorizable.defaultMinimumVoterTurnout].
     */
    public val minimumVoterTurnout: Percentage?

    /**
     * The amount of [Accessor]s that have access to this ability.
     */
    public val accessorCount: Int

    /**
     * Is [accessor] authorized to perform this ability *directly*?
     */
    public suspend fun isAuthorized(accessor: Accessor): Boolean

    public companion object {

        /**
         * The actual length a vote will take, if known.
         *
         * @see Authorizable.defaultVoteLength
         */
        public val Ability.actualVoteLength: Duration get() = voteLength ?: authorizable.defaultVoteLength

        /**
         * The minimum voter turnout, if known.
         *
         * @see Authorizable.defaultMinimumVoterTurnout
         */
        public val Ability.actualMinimumVoterTurnout: Percentage
            get() = minimumVoterTurnout ?: authorizable.defaultMinimumVoterTurnout

        /**
         * The percentage of votes a single [Accessor] represents.
         */
        public val Ability.accessorPercentage: Percentage get() = (1f / accessorCount).toPercentage()
    }
}
