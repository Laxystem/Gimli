package quest.laxla.gimli

public enum class VoteTimeoutResult {
    ForceSuccess, Evaluate, ForceFailure;

    public companion object {
        public fun of(voteTimeoutResult: Boolean?): VoteTimeoutResult = when(voteTimeoutResult) {
            true -> ForceSuccess
            null -> Evaluate
            false -> ForceFailure
        }
    }
}
