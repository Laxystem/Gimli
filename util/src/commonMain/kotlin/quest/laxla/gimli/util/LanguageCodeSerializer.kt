package quest.laxla.gimli.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

typealias LanguageCode = @Serializable(with = LanguageCodeSerializer::class) Locale

object LanguageCodeSerializer : KSerializer<Locale> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(LanguageCodeSerializer::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Locale = Locale.forLanguageTag(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Locale) = encoder.encodeString(value.toLanguageTag())
}
