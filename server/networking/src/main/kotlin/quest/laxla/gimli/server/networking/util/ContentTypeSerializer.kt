/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.networking.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import io.ktor.http.ContentType as KtorContentType

typealias ContentType = @Serializable(with = ContentTypeSerializer::class) KtorContentType

internal object ContentTypeSerializer : KSerializer<KtorContentType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(ContentTypeSerializer::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): KtorContentType = KtorContentType.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: KtorContentType) = encoder.encodeString(value.toString())
}
