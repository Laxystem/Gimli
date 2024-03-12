/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import quest.laxla.gimli.util.Percentage
import quest.laxla.gimli.util.Percentage.Companion.toPercentage
import kotlin.time.Duration

/**
 * Represents [Voter]s' ability to use [permission] on the [topic].
 */
public interface Ability : Element<Ability> {
    /**
     * The [Topic] the [permission] is performed on.
     */
    public val topic: Topic

    /**
     * The [Permission] performed on the [topic].
     */
    public val permission: String

    /**
     * The length of [Vote]s invoking this [Ability].
     *
     * [Vote]s may treat this as a *default*
     * if [topic] allows [custom vote lengths][Topic.isAllowingCustomVoteLength].
     *
     * `null` means this ability uses the [default][Topic.defaultVoteLength].
     */
    public val voteLength: Duration?

    /**
     * The [minimum voter turnout][Topic.defaultMinimumVoterTurnout] of [Vote]s invoking this [Ability].
     *
     * `null` means this ability uses the [default][Topic.defaultMinimumVoterTurnout].
     */
    public val minimumVoterTurnout: Percentage?

    /**
     * The amount of [Voter]s that have access to this ability.
     */
    public val accessorCount: Int

    /**
     * Is [accessor] authorized to perform this ability *directly*?
     */
    public suspend fun isAuthorized(accessor: Voter): Boolean

    public companion object {

        /**
         * The actual length a vote will take, if known.
         *
         * @see Topic.defaultVoteLength
         */
        public val Ability.actualVoteLength: Duration get() = voteLength ?: topic.defaultVoteLength

        /**
         * The minimum voter turnout, if known.
         *
         * @see Topic.defaultMinimumVoterTurnout
         */
        public val Ability.actualMinimumVoterTurnout: Percentage
            get() = minimumVoterTurnout ?: topic.defaultMinimumVoterTurnout

        /**
         * The percentage of votes a single [Voter] represents.
         */
        public val Ability.accessorPercentage: Percentage get() = (1f / accessorCount).toPercentage()
    }
}
