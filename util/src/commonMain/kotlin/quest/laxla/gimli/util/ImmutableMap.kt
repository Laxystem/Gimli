package quest.laxla.gimli.util

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public typealias ImmutableMap<K, V> = @Serializable(with = ImmutableMapSerializer::class) ImmutableMap<K, V>
public typealias PersistentMap<K, V> = @Serializable(with = PersistentMapSerializer::class) PersistentMap<K, V>

private val emptyMap = emptyMap<Any?, Nothing>().toPersistentMap()

@Suppress("UNCHECKED_CAST")
public fun <K, V> emptyPersistentMap(): PersistentMap<K, V> = emptyMap as PersistentMap<K, V>

public class ImmutableMapSerializer<K, V>(keySerializer: KSerializer<K>, valueSerializer: KSerializer<V>) : KSerializer<ImmutableMap<K, V>> {
    private val impl = MapSerializer(keySerializer, valueSerializer)
    override val descriptor: SerialDescriptor get() = impl.descriptor

    override fun deserialize(decoder: Decoder): ImmutableMap<K, V> = impl.deserialize(decoder).toImmutableMap()

    override fun serialize(encoder: Encoder, value: ImmutableMap<K, V>): Unit = impl.serialize(encoder, value)
}

public class PersistentMapSerializer<K, V>(keySerializer: KSerializer<K>, valueSerializer: KSerializer<V>) : KSerializer<PersistentMap<K, V>> {
    private val impl = MapSerializer(keySerializer, valueSerializer)
    override val descriptor: SerialDescriptor get() = impl.descriptor

    override fun deserialize(decoder: Decoder): PersistentMap<K, V> = impl.deserialize(decoder).toPersistentMap()

    override fun serialize(encoder: Encoder, value: PersistentMap<K, V>): Unit = impl.serialize(encoder, value)
}
