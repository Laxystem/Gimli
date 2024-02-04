package quest.laxla.gimli.server.networking.webfinger

import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.ImmutableMap
import quest.laxla.gimli.util.emptyPersistentList
import quest.laxla.gimli.util.emptyPersistentMap

@Serializable
data class JsonResourceDescriptor(
    val subject: String,
    val aliases: ImmutableList<String> = emptyPersistentList(),
    val properties: ImmutableMap<String, String> = emptyPersistentMap(),
    val links: ImmutableList<JrdLink> = emptyPersistentList()
)
