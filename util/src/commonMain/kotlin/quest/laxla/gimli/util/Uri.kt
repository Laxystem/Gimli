package quest.laxla.gimli.util

import com.eygraber.uri.Builder
import com.eygraber.uri.Uri
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Serializable typealias of [Uri][com.eygraber.uri.Uri].
 *
 * @see Uri.parse
 * @see Uri.toString
 *
 * @since 0.0.1-alpha.3
 */
public typealias Uri = @Serializable(UriSerializer::class) Uri

internal object UriSerializer : KSerializer<Uri> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(serialName = "quest.laxla.gimli.util.UrlSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uri = Uri.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }
}

/**
 * @since 0.0.1-alpha.3
 * @see Builder.authority
 */
public fun Builder.setAuthority(host: String, port: Int = -1, userInfo: String? = null): Builder =
    authority(userInfo.ifBlankOrNull { "$it@" } + host + port.takeIf { it >= 0 }?.let { ":$it" })



public fun Builder.appendToPath(path: Iterable<Any>) {
    for (segment in path) appendPath(segment.toString())
}
