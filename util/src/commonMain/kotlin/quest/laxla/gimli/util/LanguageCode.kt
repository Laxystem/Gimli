@file:Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])

package quest.laxla.gimli.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

public expect class Locale

@Serializable(with = LanguageCodeSerializer::class)
public expect class LanguageCode internal constructor(locale: Locale) {
    internal val locale: Locale
    override fun toString(): String
}

public expect fun String.toLanguageCode(): LanguageCode

public val Locale.asLanguageCode: LanguageCode get() = LanguageCode(locale = this)

internal const val LanguageCodeSerializerDescriptor = "quest.laxla.gimli.util.LanguageCode"

internal expect object LanguageCodeSerializer : KSerializer<LanguageCode>
