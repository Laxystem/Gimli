/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.networking.util

import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ContentTypeSerializer : KSerializer<ContentType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(ContentTypeSerializer::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ContentType = ContentType.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: ContentType) = encoder.encodeString(value.toString())
}
