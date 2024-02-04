package quest.laxla.gimli.ld

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import quest.laxla.gimli.util.LanguageCode
import quest.laxla.gimli.util.TextDirection
import quest.laxla.gimli.ld.property.LdpID
import quest.laxla.gimli.ld.property.LdpType

typealias Iri = String // TODO
typealias LdType = String // TODO

typealias LdObject = LdElement<Nothing>
typealias LdProperty<T> = ImmutableList<LdElement<T>>
typealias LdObjectProperty = ImmutableList<LdObject>

interface LdElement<out T> : LdpType, LdpID where T : Any {
    @SerialName("@value")
    val value: T?

    @SerialName("@language")
    val language: LanguageCode?

    @SerialName("@direction")
    val textDirection: TextDirection?
}
