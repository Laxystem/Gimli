package quest.laxla.gimli

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.Optional

public interface Tag : Element<Tag> {
    public val creationTime: Instant
    public val guild: Ref<Guild>?
    public val name: String

    @Serializable
    public data class UpdateBuilder(
        override val primaryFederalIdentifier: String,
        var guild: Optional<Ref<Guild>>
    ) : Element.Builder.Update<UpdateBuilder> {
        override fun clone(): UpdateBuilder = this
    }
}
