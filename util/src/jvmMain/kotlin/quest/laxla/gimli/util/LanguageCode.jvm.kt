@file:Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])

package quest.laxla.gimli.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal actual typealias Locale = java.util.Locale

@Serializable(with = LanguageCodeSerializer::class)
public actual open class LanguageCode internal actual constructor(public actual val locale: Locale) {
    actual override fun toString(): String = locale.toLanguageTag()
}

public actual fun String.toLanguageCode(): LanguageCode = LanguageCode(Locale.forLanguageTag(this))

internal actual object LanguageCodeSerializer : KSerializer<LanguageCode> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(LanguageCodeSerializerDescriptor, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LanguageCode = decoder.decodeString().toLanguageCode()

    override fun serialize(encoder: Encoder, value: LanguageCode): Unit = encoder.encodeString(value.toString())
}
