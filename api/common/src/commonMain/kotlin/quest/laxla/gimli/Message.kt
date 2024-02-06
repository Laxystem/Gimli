package quest.laxla.gimli

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.emptyString

public interface Message : Element.Federalized<Message> { // TODO: separate messages into the revision system
    public val author: Profile
    public val railway: Railway?
    public val replyTo: Message?
    public val title: String
    public val summary: String
    public val body: String
    public val tags: ImmutableList<Ref<Tag>>
    public val explicitMentions: ImmutableList<Ref<Profile>>
    public val replies: Flow<Ref<Message>>

    @Serializable
    public data class CreateBuilder(
        var author: Profile,
        var replyTo: Message? = null,
        var railway: Railway? = null,
        var title: String = emptyString(),
        var summary: String = emptyString(),
        var body: String = emptyString(),
        val tags: MutableList<Ref<Tag>> = mutableListOf(),
        val explicitMentions: MutableList<Ref<Profile>> = mutableListOf()
    ) : Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    // TODO: update builder
}
