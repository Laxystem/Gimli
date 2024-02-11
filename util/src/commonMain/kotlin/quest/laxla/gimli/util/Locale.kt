/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package quest.laxla.gimli.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public expect class Locale

public expect fun String.toLocale(): Locale

public expect val CurrentLocale: Locale

/**
 * Formats this [Locale] in a user-readable way.
 */
public expect val Locale.formatted: String

/**
 * Formats this [Locale] as an IETF BCP 47 code.
 */
public expect val Locale.code: String

/**
 * Formats this [Locale]'s language in a user-readable way.
 */
public expect val Locale.formattedLanguage: String

/**
 * Formats this [Locale]'s language as an ISO 639 alpha-2 or alpha-3 language code, with a preference for the former.
 */
public expect val Locale.languageCode: String

/**
 * Formats this [Locale]'s script in a user-readable way.
 */
public expect val Locale.formattedScript: String

/**
 * Formats this [Locale]'s script as an ISO 15924 alpha-4 script code.
 */
public expect val Locale.scriptCode: String

/**
 * Formats this [Locale]'s region in a user-readable way.
 */
public expect val Locale.formattedRegion: String

/**
 * Formats this [Locale]'s region as an ISO 3166 alpha-2 country code or UN M.49 numeric-3 area code.
 */
public expect val Locale.regionCode: String

internal object IetfBcp47Serializer : KSerializer<Locale> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(
            serialName = "quest.laxla.gimli.util.IetfBcp47Serializer",
            PrimitiveKind.STRING
        )

    override fun deserialize(decoder: Decoder): Locale = decoder.decodeString().toLocale()

    override fun serialize(encoder: Encoder, value: Locale): Unit = encoder.encodeString(value.toString())
}

public typealias IetfBcp47 = @Serializable(with = IetfBcp47Serializer::class) Locale
