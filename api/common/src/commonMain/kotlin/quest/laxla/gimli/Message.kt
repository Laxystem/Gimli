package quest.laxla.gimli

import kotlinx.coroutines.flow.emptyFlow
import quest.laxla.gimli.util.CatchingFlow
import quest.laxla.gimli.util.emptyString

public sealed interface Message {
    public val author: Profile
    public val railway: Railway?
    public val replyTo: Message?
    public val title: String
    public val summary: String
    public val body: String
    public val tags: CatchingFlow<Tag>
    public val explicitMentions: CatchingFlow<Profile>

    public interface Actual : Message, Element.Federalized<Actual> {
        public val replies: CatchingFlow<Message> get() = emptyFlow()
    }

    public data class CreateBuilder(
        override val author: Profile,
        override var replyTo: Message? = null,
        override var railway: Railway? = null,
        override var title: String = emptyString(),
        override var summary: String = emptyString(),
        override var body: String = emptyString(),
        override var tags: CatchingFlow<Tag> = emptyFlow(),
        override var explicitMentions: CatchingFlow<Profile.Actual> = emptyFlow()
        // TODO: replace direct explicit reference with identifier?
    ) : Message, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }

    // TODO: update builder
}
