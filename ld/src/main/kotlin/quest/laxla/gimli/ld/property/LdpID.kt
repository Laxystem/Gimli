package quest.laxla.gimli.ld.property

import kotlinx.serialization.SerialName
import quest.laxla.gimli.ld.Iri

interface LdpID {
    @SerialName("@id")
    val id: Iri?
}
