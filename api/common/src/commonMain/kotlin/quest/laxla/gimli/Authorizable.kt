package quest.laxla.gimli

import quest.laxla.gimli.util.Maybe
import quest.laxla.gimli.util.Percentage
import quest.laxla.gimli.util.Percentage.Companion.percent
import quest.laxla.gimli.util.Unknown
import quest.laxla.gimli.util.isNotFalse
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

public interface Authorizable : Element.Federalized<Authorizable> { // TODO: turn into a proper element
    // votes

    @Federated(fallback = Federated.Fallback.Default)
    public val allowedActionVoteLength: ClosedRange<Duration> get() = 7.days..30.days

    @Federated(fallback = Federated.Fallback.Default)
    public val defaultActionVoteLength: Duration get() = Duration.INFINITE

    @Federated(fallback = Federated.Fallback.Default)
    public val defaultMinimumVoterTurnout: Percentage get() = 75.percent

    @Federated(fallback = Federated.Fallback.Default)
    public val isAllowingActionFailureVetoes: Boolean get() = false

    @Federated(fallback = Federated.Fallback.Default)
    public val isAllowingActionRetraction: Boolean get() = false

    @Federated(fallback = Federated.Fallback.Default)
    public val isAllowingActionSuccessVetoes: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    public val isAllowingNeutralActionResponses: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    public val isAllowingTimeIndependentActions: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    public val publishActionResponses: Maybe get() = Unknown

    // time-limited votes
    @Federated(fallback = Federated.Fallback.Default)
    public val isAutomaticallyEvaluatingTimeLimitedActions: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    public val actionTimeoutResult: VoteTimeoutResult get() = VoteTimeoutResult.Evaluate

    // time-independent votes
    @Federated(fallback = Federated.Fallback.Default)
    public val isAutomaticallyEvaluatingTimeIndependentActions: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    public val isAllowingTimeIndependentActionResponseChange: Maybe get() = Unknown

    public companion object {
        public val Authorizable.isValid: Boolean
            get() = ((isAllowingTimeIndependentActions.isNotFalse && defaultActionVoteLength == Duration.INFINITE) || defaultActionVoteLength in allowedActionVoteLength)
                    && allowedActionVoteLength.start > Duration.ZERO && allowedActionVoteLength.endInclusive < Duration.INFINITE
                    && (isAutomaticallyEvaluatingTimeLimitedActions.isNotFalse || actionTimeoutResult == VoteTimeoutResult.Evaluate)
    }
}
