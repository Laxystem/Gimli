/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

import kotlinx.collections.immutable.*
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

public fun <T> emptyPersistentList(): PersistentList<T> = persistentListOf()

internal class ImmutableListSerializer<E>(eSerializer: KSerializer<E>) : KSerializer<KtxImmutableList<E>> {
    private val impl = ListSerializer(eSerializer)
    override val descriptor: SerialDescriptor get() = impl.descriptor

    override fun deserialize(decoder: Decoder): KtxImmutableList<E> = impl.deserialize(decoder).toImmutableList()

    override fun serialize(encoder: Encoder, value: KtxImmutableList<E>): Unit = impl.serialize(encoder, value)
}

internal class PersistentListSerializer<E>(eSerializer: KSerializer<E>) : KSerializer<KtxPersistentList<E>> {
    private val impl = ListSerializer(eSerializer)
    override val descriptor: SerialDescriptor get() = impl.descriptor

    override fun deserialize(decoder: Decoder): KtxPersistentList<E> = impl.deserialize(decoder).toPersistentList()

    override fun serialize(encoder: Encoder, value: KtxPersistentList<E>): Unit = impl.serialize(encoder, value)
}
