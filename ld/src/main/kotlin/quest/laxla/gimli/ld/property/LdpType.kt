package quest.laxla.gimli.ld.property

import kotlinx.serialization.SerialName
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.ld.LdType

interface LdpType {
    @SerialName("@id")
    val type: ImmutableList<LdType>
}