package quest.laxla.gimli.ld

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import quest.laxla.gimli.ld.impl.LdElementImpl

typealias SerializableLdElement<T> = @Serializable(with = LdElementSerializer::class) LdElement<T>

class LdElementSerializer<T>(tSerializer: KSerializer<T>) : KSerializer<LdElement<T>> where T : Any {
    private val impl = LdElementImpl.serializer(tSerializer)

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor(LdElement::class.qualifiedName!!, impl.descriptor)

    override fun deserialize(decoder: Decoder): LdElement<T> = impl.deserialize(decoder)

    override fun serialize(encoder: Encoder, value: LdElement<T>) = impl.serialize(
        encoder,
        LdElementImpl(
            id = value.id,
            type = value.type,
            value = value.value,
            language = value.language,
            textDirection = value.textDirection
        )
    )
}