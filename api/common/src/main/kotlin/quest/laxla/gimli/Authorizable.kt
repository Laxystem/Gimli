package quest.laxla.gimli

import quest.laxla.gimli.util.Maybe
import quest.laxla.gimli.util.Percentage
import quest.laxla.gimli.util.Percentage.Companion.percent
import quest.laxla.gimli.util.Unknown
import quest.laxla.gimli.util.isNotFalse
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

interface Authorizable : Element.Federalized<Authorizable> {
    // votes

    @Federated(fallback = Federated.Fallback.Default)
    val allowedActionVoteLength: ClosedRange<Duration> get() = 7.days..30.days

    @Federated(fallback = Federated.Fallback.Default)
    val defaultActionVoteLength: Duration get() = Duration.INFINITE

    @Federated(fallback = Federated.Fallback.Default)
    val defaultMinimumVoterTurnout: Percentage get() = 75.percent

    @Federated(fallback = Federated.Fallback.Default)
    val isAllowingActionFailureVetoes: Boolean get() = false

    @Federated(fallback = Federated.Fallback.Default)
    val isAllowingActionRetraction: Boolean get() = false

    @Federated(fallback = Federated.Fallback.Default)
    val isAllowingActionSuccessVetoes: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    val isAllowingNeutralActionResponses: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    val isAllowingTimeIndependentActions: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    val publishActionResponses: Maybe get() = Unknown

    // time-limited votes
    @Federated(fallback = Federated.Fallback.Default)
    val isAutomaticallyEvaluatingTimeLimitedActions: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    val actionTimeoutResult: VoteTimeoutResult get() = VoteTimeoutResult.Evaluate

    // time-independent votes
    @Federated(fallback = Federated.Fallback.Default)
    val isAutomaticallyEvaluatingTimeIndependentActions: Maybe get() = Unknown

    @Federated(fallback = Federated.Fallback.Default)
    val isAllowingTimeIndependentActionResponseChange: Maybe get() = Unknown

    companion object {
        val Authorizable.isValid
            get() = ((isAllowingTimeIndependentActions.isNotFalse && defaultActionVoteLength == Duration.INFINITE) || defaultActionVoteLength in allowedActionVoteLength)
                    && allowedActionVoteLength.start > Duration.ZERO && allowedActionVoteLength.endInclusive < Duration.INFINITE
                    && (isAutomaticallyEvaluatingTimeLimitedActions.isNotFalse || actionTimeoutResult == VoteTimeoutResult.Evaluate)
    }
}
