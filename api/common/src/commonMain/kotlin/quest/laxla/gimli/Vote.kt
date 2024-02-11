/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import quest.laxla.gimli.Ability.Companion.accessorPercentage
import quest.laxla.gimli.Ability.Companion.actualMinimumVoterTurnout
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.Maybe
import quest.laxla.gimli.util.implies

public interface Vote : Element.Federalized<Vote> { // TODO: add properties
    /**
     * The [Accessor] that has created this [Vote].
     */
    public val sponsor: Ref<Accessor>

    /**
     * The [Ability] this vote is intended to invoke.
     */
    public val ability: Ability

    /**
     * The responses to this vote, and if publicized, the responders themselves.
     */
    public val responses: ImmutableList<Pair<Ref<Accessor>?, Response>>

    public val plannedEndTime: Instant?

    /**
     * The time at which this vote has ended.
     *
     * Mustn't be in the future.
     */
    public val actualEndTime: Instant?

    public operator fun set(accessor: Accessor, response: Response)

    public enum class TimeoutResult {
        ForceSuccess, Evaluate, ForceFailure
    }

    public enum class Response {
        For, Neutral, Against
    }

    /**
     * Describes how neutral responses interact with the [vote success percentage]][Authorizable.defaultVoteSuccessPercentage].
     */
    public enum class NeutralResponseBehaviour {
        PassivelyFor, Uncounted, PassivelyAgainst;

        public companion object {
            public val Forbidden: NeutralResponseBehaviour? = null
        }
    }

    public companion object {
        public val Vote.responders: Sequence<Ref<Accessor>> get() = responses.asSequence().mapNotNull { it.first }
        public val Vote.results: Sequence<Response> get() = responses.asSequence().map { it.second }
        public val Vote.isTimeLimited: Boolean get() = plannedEndTime != null
        public val Vote.isTimeUnlimited: Boolean get() = plannedEndTime == null
        public val Vote.isAutomaticallyEvaluating: Boolean
            get() = if (isTimeLimited) ability.authorizable.isAutomaticallyEvaluatingTimeLimitedVotes
            else ability.authorizable.isAutomaticallyEvaluatingTimeUnlimitedVotes

        /**
         * Counts the results of this vote,
         * and returns them as a [Triple] of [For][Response.For],
         * [Neutral][Response.Neutral], and [Against][Response.Against].
         */
        public fun Vote.countResults(): Triple<Int, Int, Int> {
            var affirmative = 0
            var neutral = 0
            var negative = 0

            for (response in results) when (response) {
                Response.For -> affirmative++
                Response.Neutral -> neutral++
                Response.Against -> negative++
            }

            return Triple(affirmative, negative, negative)
        }

        /**
         * Evaluate the results of this [Vote], returning `true` if it succeeded.
         */
        public fun Vote.evaluate(): Boolean {
            val (affirmative, neutral, negative) = countResults()


            val accessorPercentage = ability.accessorPercentage
            val affirmativePercentage = accessorPercentage * affirmative
            val neutralPercentage = accessorPercentage * neutral
            val negativePercentage = accessorPercentage * negative

            if (affirmative < ability.vote)

            val voterTurnout =
                if (ability.authorizable.isAllowingNeutralResponses) affirmativePercentage + neutralPercentage + negativePercentage
                else affirmativePercentage + negativePercentage

            return (ability.actualMinimumVoterTurnout <= voterTurnout) implies (affirmative > negative && affirmative >)
        }

        /**
         * Returns the final results of this [Vote], if it ended already.
         */
        public fun Vote.finalResults(time: Instant = Clock.System.now()): Maybe = when {
            actualEndTime != null -> evaluate()
            isTimeLimited && plannedEndTime!! < time -> evaluate()
            isAutomaticallyEvaluating -> {

            }
        }
    }
}
