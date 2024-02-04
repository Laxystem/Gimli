package quest.laxla.gimli

enum class FollowingStatus {
    Accepted, Requested;

    companion object {
        val FollowingStatus?.isRequested get() = this != null
        val FollowingStatus?.isAccepted get() = this == Accepted
    }
}
