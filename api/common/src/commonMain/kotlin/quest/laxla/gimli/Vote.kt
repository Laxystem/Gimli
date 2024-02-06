package quest.laxla.gimli

public interface Vote : Element.Federalized<Vote> { // TODO: add properties
    public enum class TimeoutResult {
        ForceSuccess, Evaluate, ForceFailure;

        public companion object {
            public fun of(voteTimeoutResult: Boolean?): TimeoutResult = when(voteTimeoutResult) {
                true -> ForceSuccess
                null -> Evaluate
                false -> ForceFailure
            }
        }
    }
}
