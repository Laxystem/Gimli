package quest.laxla.gimli.ld.impl

import kotlinx.serialization.Serializable
import quest.laxla.gimli.ld.*
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.LanguageCode
import quest.laxla.gimli.util.TextDirection

@Serializable
internal data class LdElementImpl<out T>(
    override val id: Iri? = null,
    override val type: ImmutableList<LdType> = quest.laxla.gimli.util.emptyPersistentList(),
    override val value: T? = null,
    override val language: LanguageCode? = null,
    override val textDirection: TextDirection? = null
) : LdElement<T> where T : Any