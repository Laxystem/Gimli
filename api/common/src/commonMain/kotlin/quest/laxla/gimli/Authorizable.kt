package quest.laxla.gimli

import quest.laxla.gimli.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

/**
 * Represents something that [Accessor]s can have [Permission]s at.
 */
public interface Authorizable : Element.Federalized<Authorizable> {
    /**
     * The minimum and maximum time a time-limited vote may last.
     *
     * If the minimum and the maximum are equal, time-limited votes are forbidden.
     * The minimum must be bigger than or equal to [Duration.ZERO] and smaller than the maximum.
     * Maximum of [Duration.INFINITE] stands for no limit.
     *
     * Fallbacks to [Duration.ZERO] and [Duration.INFINITE].
     */
    public val allowedVoteLength: ClosedRange<Duration>

    /**
     * The default time a vote lasts.
     *
     * [Duration.INFINITE] stands for time-unlimited votes by default.
     * Must be within [allowedVoteLength].
     * Overridable by [Abilities][Ability].
     *
     * Fallbacks to 14 [days].
     */
    public val defaultVoteLength: Duration

    /**
     * The default percent of [Accessor]s that need to vote for the vote to succeed.
     *
     * Overridable by [Abilities][Ability].
     *
     * Fallbacks to `null` - the Client SHOULD clarify it is unknown.
     */
    public val defaultMinimumVoterTurnout: Percentage?

    /**
     * Allows [Accessor]s with the [Permission.BuiltIn.Veto] permission to force a vote to fail.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingVetoes: Maybe

    /**
     * Allows [Accessor]s with the [Permission.BuiltIn.Bypass] permission to force a vote to succeed.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingBypasses: Maybe

    /**
     * Allows [Accessor]s to force votes they've created to fail.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingVoteRetraction: Maybe

    /**
     * Allows accessors to respond "Neutral",
     * meaning they're counted for [defaultMinimumVoterTurnout],
     * without affecting the vote's result.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingNeutralResponses: Maybe

    /**
     * Allows time-independent votes, that is, votes that won't time out.
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingTimeIndependentVotes: Maybe

    /**
     * Are action responses public?
     *
     * The client MUST clarify it is unknown.
     */
    public val publishVoteResponses: Maybe

    /**
     * Are time-limited votes automatically evaluated when the remaining voters cannot
     * change its result?
     *
     * Votes will only be evaluated if the minimum voter turnout is full.
     * Must be `true` or [Unknown] if [voteTimeoutResult] isn't [VoteTimeoutResult.Evaluate].
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAutomaticallyEvaluatingTimeLimitedVotes: Maybe

    /**
     * When a time-limited vote time-outs, what happens?
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val voteTimeoutResult: Vote.TimeoutResult

    /**
     * Can votes be created with a length different from the invoked [Ability]'s?
     *
     * It is the client's responsibility to handle [Unknown] values.
     * Note votes must still adhere to the [allowedVoteLength].
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingCustomVoteLength: Maybe

    /**
     * Are time-unlimited votes automatically evaluated when the remaining voters cannot
     * change its result?
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAutomaticallyEvaluatingTimeUnlimitedVotes: Maybe

    /**
     * Can voters change their response on time-unlimited votes?
     *
     * It is the client's responsibility to handle [Unknown] values.
     */
    public val isAllowingTimeUnlimitedVoteResponseChange: Maybe get() = Unknown

    /**
     * Gets the [Ability] of this [Authorizable] to perform [permission].
     */
    public suspend fun getAbility(permission: Permission)

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

    public companion object : Element.Informer {
        public val Authorizable.isValid: Boolean
            get() = ((isAllowingTimeIndependentVotes.isNotFalse && defaultVoteLength == Duration.INFINITE) || defaultVoteLength in allowedVoteLength)
                    && allowedVoteLength.start > Duration.ZERO
                    && (isAutomaticallyEvaluatingTimeLimitedVotes.isNotFalse || voteTimeoutResult == Vote.TimeoutResult.Evaluate)

        override fun federalIdentifierFor(numeralIdentifier: Long, domain: String): String =
            FederalIdentifier.ofAuthorization(domain) + "/authorizable/$numeralIdentifier"
    }
}
