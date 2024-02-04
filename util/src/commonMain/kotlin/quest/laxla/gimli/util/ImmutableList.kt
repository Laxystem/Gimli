package quest.laxla.gimli.util

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.collections.immutable.ImmutableList as KtxImmutableList
import kotlinx.collections.immutable.PersistentList as KtxPersistentList

public typealias ImmutableList<E> = @Serializable(with = ImmutableListSerializer::class) KtxImmutableList<E>
public typealias PersistentList<E> = @Serializable(with = PersistentListSerializer::class) KtxPersistentList<E>

private val emptyList = emptyList<Nothing>().toPersistentList()

public fun <T> emptyPersistentList(): PersistentList<T> = emptyList

public class ImmutableListSerializer<E>(eSerializer: KSerializer<E>) : KSerializer<KtxImmutableList<E>> {
    private val impl = ListSerializer(eSerializer)
    override val descriptor: SerialDescriptor get() = impl.descriptor

    override fun deserialize(decoder: Decoder): KtxImmutableList<E> = impl.deserialize(decoder).toImmutableList()

    override fun serialize(encoder: Encoder, value: KtxImmutableList<E>): Unit = impl.serialize(encoder, value)
}

public class PersistentListSerializer<E>(eSerializer: KSerializer<E>) : KSerializer<KtxPersistentList<E>> {
    private val impl = ListSerializer(eSerializer)
    override val descriptor: SerialDescriptor get() = impl.descriptor

    override fun deserialize(decoder: Decoder): KtxPersistentList<E> = impl.deserialize(decoder).toPersistentList()

    override fun serialize(encoder: Encoder, value: KtxPersistentList<E>): Unit = impl.serialize(encoder, value)
}