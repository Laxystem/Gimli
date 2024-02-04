package quest.laxla.gimli.server.networking.webfinger

import io.ktor.http.*
import kotlinx.serialization.Serializable
import quest.laxla.gimli.server.networking.util.ContentTypeSerializer
import quest.laxla.gimli.util.ImmutableMap
import quest.laxla.gimli.util.LanguageCode
import quest.laxla.gimli.util.emptyPersistentMap

@Serializable
data class JrdLink(
    val rel: String,
    val type: @Serializable(with = ContentTypeSerializer::class) ContentType? = null,
    val href: String? = null,
    val titles: ImmutableMap<LanguageCode, String> = emptyPersistentMap()
)
