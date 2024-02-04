package quest.laxla.gimli

enum class VoteTimeoutResult {
    ForceSuccess, Evaluate, ForceFailure;

    companion object {
        fun of(voteTimeoutResult: Boolean?) = when(voteTimeoutResult) {
            true -> ForceSuccess
            null -> Evaluate
            false -> ForceFailure
        }
    }
}
