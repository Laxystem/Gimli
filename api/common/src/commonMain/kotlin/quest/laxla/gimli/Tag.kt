package quest.laxla.gimli

import kotlinx.datetime.Instant

public interface Tag : Element<Tag> {
    public val creationTime: Instant
    public val guild: Guild
    public val name: String
}
