package quest.laxla.gimli

import quest.laxla.gimli.util.Percentage
import kotlin.time.Duration

/**
 * An ability usable by [Accessor]s on [authorizable].
 */
public interface Ability : Element<Ability> {
    public val permission: Permission
    public val authorizable: Authorizable

    /**
     * The length of [Vote]s invoking this [Ability].
     *
     * [Vote]s may treat this as a *default* if the [authorizable] this ability is bound to
     * [allows custom vote lengths][Authorizable.isAllowingCustomVoteLength].
     */
    public val voteLength: Duration?

    /**
     * TODO: documentation
     */
    public val minimumVoterTurnout: Percentage?

    public companion object {
        public val Ability.actualVoteLength: Duration get() = voteLength ?: authorizable.defaultVoteLength

        public val Ability.actualMinimumVoterTurnout: Percentage? get() = minimumVoterTurnout ?: authorizable.defaultMinimumVoterTurnout
    }
}
