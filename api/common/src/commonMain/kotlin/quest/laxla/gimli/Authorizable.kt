/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.*
import kotlin.time.Duration

/**
 * Represents something that [Accessor]s can have [Permission]s at.
 */
public interface Authorizable : Element.Federalized<Authorizable> {
    // TODO: refine federated democracy to match FEP-5a4f
    /**
     * The minimum and maximum time a time-limited vote may last.
     *
     * If the range is [empty][ClosedRange.isEmpty], time-limited votes are forbidden.
     * The minimum must be bigger than or equal to [Duration.ZERO].
     *
     * Maximum of [Duration.INFINITE] stands for no limit.
     *
     * Fallbacks to [Duration.ZERO] and [Duration.INFINITE].
     */
    public val allowedVoteLength: ClosedRange<Duration>

    /**
     * The default time a vote lasts.
     *
     * [Duration.INFINITE] stands for time-unlimited votes by default.
     * Must be within [allowedVoteLength] and bigger than [Duration.ZERO].
     * Overridable by [Abilities][Ability].
     */
    public val defaultVoteLength: Duration

    /**
     * The percentage of accessors that must vote [For][Vote.Response.For] for this vote to succeed.
     */
    public val defaultVoteSuccessPercentage: Percentage

    /**
     * The default percent of [Accessor]s that need to vote for the vote to succeed.
     *
     * Overridable by [Abilities][Ability].
     *
     * Fallbacks to `null` - the Client SHOULD clarify it is unknown.
     */
    public val defaultMinimumVoterTurnout: Percentage

    /**
     * Allows [Accessor]s with the [Permission.BuiltIn.Veto] permission to force a vote to fail.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingVetoes: Boolean

    /**
     * Allows [Accessor]s with the [Permission.BuiltIn.Bypass] permission to force a vote to succeed.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingBypasses: Boolean

    /**
     * Allows [Accessor]s to force votes they've created to fail.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingVoteRetraction: Boolean

    /**
     * Describes the behavior of neutral when calculating the success percentage.
     *
     * Neutral votes always counted for [voter turnout][defaultMinimumVoterTurnout],
     * and are never counted when comparing success responses to failure responses.
     */
    public val neutralResponseBehaviour: Vote.NeutralResponseBehaviour?

    /**
     * Allows time-independent votes, that is, votes that won't time out.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingTimeIndependentVotes: Boolean

    /**
     * Are action responses public?
     *
     * The client MUST clarify it is unknown.
     */
    public val publishVoteResponses: Boolean

    /**
     * Are time-limited votes automatically evaluated when the remaining voters cannot
     * change its result?
     *
     * Votes will only be evaluated if the minimum voter turnout is full.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAutomaticallyEvaluatingTimeLimitedVotes: Boolean

    /**
     * Can votes be created with a length different from the invoked [Ability]'s?
     *
     * It is the client's responsibility to handle [Unknown] values.
     * Note votes must still adhere to the [allowedVoteLength].
     *
     * It is the client's responsibility to handle [Unknown] values.
     *
     * If a client tries to create a vote with a custom length and this property is `false`,
     * it is silently dropped.
     */
    public val isAllowingCustomVoteLength: Boolean

    /**
     * Are time-unlimited votes automatically evaluated when the remaining voters cannot
     * change its result?
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAutomaticallyEvaluatingTimeUnlimitedVotes: Boolean

    /**
     * Can voters change their response on time-unlimited votes?
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingTimeUnlimitedVoteResponseChange: Boolean

    /**
     * Gets the [Ability] of this [Authorizable] to perform [permission].
     */
    public suspend fun getAbility(permission: Permission)

    @Serializable
    public data class UpdateBuilder(
        override val primaryFederalIdentifier: String,
        public var minimumAllowedVoteLength: Optional<Int> = Optional.Empty,
        public var maximumAllowedVoteLength: Optional<Int> = Optional.Empty,
        public var defaultVoteLength: Optional<Duration> = Optional.Empty,
        public var minimumVoterTurnout: Optional<Percentage> = Optional.Empty,
        public var isAllowingVetoes: Optional<Boolean> = Optional.Empty,
        public var isAllowingBypasses: Optional<Boolean> = Optional.Empty,
        public var isAllowingRetraction: Optional<Boolean> = Optional.Empty,
        public var isAllowingNeutralResponses: Optional<Boolean> = Optional.Empty,
        public var isAllowingTimeUnlimitedVotes: Optional<Boolean> = Optional.Empty,
        public var publishVoteResponses: Optional<Boolean> = Optional.Empty,
        public var isAutomaticallyEvaluatingTimeLimitedVotes: Optional<Boolean> = Optional.Empty,
        public var voteTimeoutResult: Optional<Vote.TimeoutResult> = Optional.Empty,
        public var isAutomaticallyEvaluatingTimeUnlimitedVotes: Optional<Boolean> = Optional.Empty,
        public var isAllowingTimeUnlimitedVoteResponseChange: Optional<Boolean> = Optional.Empty,
    ) : Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = this
    }

    public companion object {
        public val Authorizable.isValid: Boolean
            get() = allowedVoteLength.start >= Duration.ZERO
                    && if (defaultVoteLength == Duration.INFINITE) isAllowingTimeIndependentVotes else defaultVoteLength in allowedVoteLength
    }
}
