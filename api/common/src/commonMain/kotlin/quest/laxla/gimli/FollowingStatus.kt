package quest.laxla.gimli

public enum class FollowingStatus {
    Accepted, Requested;

    public companion object {
        public val FollowingStatus?.isRequested: Boolean get() = this != null
        public val FollowingStatus?.isAccepted: Boolean get() = this == Accepted
    }
}
