package quest.laxla.gimli

import kotlinx.coroutines.flow.emptyFlow
import quest.laxla.gimli.util.CatchingFlow
import quest.laxla.gimli.util.emptyString

sealed interface Message {
    val author: Profile
    val railway: Railway? get() = null
    val replyTo: Message? get() = null
    val title: String get() = emptyString()
    val summary: String get() = emptyString()
    val body: String get() = emptyString()
    val tags: CatchingFlow<Tag> get() = emptyFlow()
    val explicitMentions: CatchingFlow<Profile> get() = emptyFlow()

    interface Actual : Message, Element.Federalized<Actual> {
        val replies: CatchingFlow<Message> get() = emptyFlow()
    }

    data class CreateBuilder(
        override val author: Profile,
        override var replyTo: Message? = null,
        override var railway: Railway? = null,
        override var title: String = emptyString(),
        override var summary: String = emptyString(),
        override var body: String = emptyString(),
        override var tags: CatchingFlow<Tag> = emptyFlow(),
        override var explicitMentions: CatchingFlow<Profile.Actual> = emptyFlow()
    ) : Message, Element.Builder.Create<CreateBuilder> {
        override fun clone(): CreateBuilder = copy()
    }
}
