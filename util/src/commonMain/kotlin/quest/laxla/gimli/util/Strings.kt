/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Returns an empty [String].
 */
public fun emptyString(): String = ""

/**
 * Converts `null`s to empty [String]s.
 */
public fun Any?.toStringOrEmpty(): String = this?.toString().orEmpty()

public const val Ellipsis: Char = '…'
public const val DotDotDot: String = "..."
public const val QuoteDotDotDot: String = "[$DotDotDot]"
public const val QuoteEllipsis: String = "[$Ellipsis]"

/**
 * If the text exceeds [maximumLength], trims it, appending [replaceWith].
 *
 * The result's [length][String.length] will *always* be smaller than equal to [maximumLength].
 *
 * @see Ellipsis
 * @see DotDotDot
 * @see QuoteDotDotDot
 * @see QuoteEllipsis
 */
public fun CharSequence.trimmedAfter(maximumLength: Int, replaceWith: CharSequence = emptyString()): String =
    substring(range = 0..<maximumLength - replaceWith.length) + replaceWith

/**
 * If the text exceeds [maximumLength], trims it, appending [replaceWith] if not null.
 *
 * The result's [length][String.length] will *always* be smaller than equal to [maximumLength].
 *
 * @see Ellipsis
 * @see DotDotDot
 * @see QuoteDotDotDot
 * @see QuoteEllipsis
 */
public fun CharSequence.trimmedAfter(maximumLength: Int, replaceWith: Char? = null): String =
    trimmedAfter(maximumLength, replaceWith.toStringOrEmpty())

/**
 * Returns `true` if this char sequence isn't `null` nor empty nor only consists of whitespace character.
 *
 * @see isNullOrBlank
 */
@OptIn(ExperimentalContracts::class)
public fun CharSequence?.isNotNullNorBlank(): Boolean {
    contract {
        returns(value = true) implies (this@isNotNullNorBlank != null)
    }

    return this != null && this.isBlank()
}

/**
 * Executes [block] if this char sequence isn't `null` nor empty nor only consists of whitespace character.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> T?.ifNotBlankNorNull(block: (T) -> Unit) where T : CharSequence {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    if (isNotNullNorBlank()) block(this)
}

/**
 * Returns [block] if this char sequence is `null`, [empty][isEmpty] or [blank][isBlank].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> T.ifBlankOrNull(block: (T) -> T): T where T : CharSequence? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    return if (isNullOrBlank()) block(this) else this
}

/**
 * Returns the first match of [regex], beginning at the specified [startIndex].
 *
 * @throws IndexOutOfBoundsException
 */
public fun CharSequence.find(regex: Regex, startIndex: Int = 0): MatchResult? = regex.find(input = this, startIndex)

/**
 * Returns a sequence of all matches of [regex], beginning at the specified [startIndex].
 *
 * @throws IndexOutOfBoundsException
 */
public fun CharSequence.findAll(regex: Regex, startIndex: Int = 0): Sequence<MatchResult> =
    regex.findAll(input = this, startIndex)

/**
 * Returns the last match of [regex], beginning at the specified [startIndex].
 *
 * @throws IndexOutOfBoundsException
 */
public fun CharSequence.findLast(regex: Regex, startIndex: Int = 0): MatchResult? =
    regex.findAll(input = this, startIndex).lastOrNull()

/**
 * Returns a substring before the first match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun CharSequence.substringBefore(
    delimiterRegex: Regex,
    missingDelimiterValue: CharSequence = subSequence(indices),
    includingMatch: Boolean = false
): CharSequence {
    val match = find(delimiterRegex)
    return if (match == null) missingDelimiterValue
    else subSequence(if (includingMatch) 0..match.range.last else 0..<match.range.first)
}

/**
 * Returns a substring before the first match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun String.substringBefore(
    delimiterRegex: Regex,
    missingDelimiterValue: String = this,
    includingMatch: Boolean = false
): String = (this as CharSequence).substringBefore(delimiterRegex, missingDelimiterValue, includingMatch).toString()

/**
 * Returns a substring after the first match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun CharSequence.substringAfter(
    delimiterRegex: Regex,
    missingDelimiterValue: CharSequence = subSequence(indices),
    includingMatch: Boolean = false
): CharSequence {
    val match = find(delimiterRegex)
    return if (match == null) missingDelimiterValue
    else subSequence((if (includingMatch) match.range.first else match.range.last + 1)..<length)
}

/**
 * Returns a substring after the first match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun String.substringAfter(
    delimiterRegex: Regex,
    missingDelimiterValue: String = this,
    includingMatch: Boolean = false
): String = (this as CharSequence).substringAfter(delimiterRegex, missingDelimiterValue, includingMatch).toString()


/**
 * Returns a substring before the last match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun CharSequence.substringBeforeLast(
    delimiterRegex: Regex,
    missingDelimiterValue: CharSequence = subSequence(indices),
    includingMatch: Boolean = false
): CharSequence {
    val match = findLast(delimiterRegex)
    return if (match == null) missingDelimiterValue
    else subSequence(if (includingMatch) 0..match.range.last else 0..<match.range.first)
}

/**
 * Returns a substring before the last match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun String.substringBeforeLast(
    delimiterRegex: Regex,
    missingDelimiterValue: String = this,
    includingMatch: Boolean = false
): String = (this as CharSequence).substringBeforeLast(delimiterRegex, missingDelimiterValue, includingMatch).toString()

/**
 * Returns a substring after the last match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun CharSequence.substringAfterLast(
    delimiterRegex: Regex,
    missingDelimiterValue: CharSequence = subSequence(indices),
    includingMatch: Boolean = false
): CharSequence {
    val match = findLast(delimiterRegex)
    return if (match == null) missingDelimiterValue
    else subSequence((if (includingMatch) match.range.first else match.range.last + 1)..<length)
}

/**
 * Returns a substring after the last match of [delimiterRegex].
 * If the string does not contain the delimiter, returns [missingDelimiterValue] which defaults to the original string.
 *
 * @param includingMatch include the match in the resulting string
 */
public fun String.substringAfterLast(
    delimiterRegex: Regex,
    missingDelimiterValue: String = this,
    includingMatch: Boolean = false
): String = (this as CharSequence).substringAfterLast(delimiterRegex, missingDelimiterValue, includingMatch).toString()

/**
 * Returns `true` if a substring of this string
 * (starting at the specified [startIndex])
 * starts with another substring matching [prefixRegex].
 *
 * @throws IndexOutOfBoundsException
 */
public fun CharSequence.startsWith(prefixRegex: Regex, startIndex: Int = 0): Boolean =
    find(prefixRegex, startIndex)?.range?.start == startIndex


/**
 * Returns `true` if a substring of this string
 * (starting at the specified [startIndex])
 * ends with another substring matching [suffixRegex].
 *
 * @throws IndexOutOfBoundsException
 */
public fun CharSequence.endsWith(suffixRegex: Regex, startIndex: Int = 0): Boolean =
    findLast(suffixRegex, startIndex)?.range?.endInclusive == lastIndex

/**
 * If this [CharSequence] starts with a subsequence matching [prefixRegex], returns a new char sequence
 * with the prefix removed.
 * Otherwise, returns a new char sequence with the same characters.
 */
public fun CharSequence.removePrefix(prefixRegex: Regex): CharSequence = find(prefixRegex).let { prefix ->
    if (prefix == null || prefix.range.first != 0) subSequence(indices)
    else subSequence(prefix.value.length..<length)
}

/**
 * If this [String] starts with a substring matching [prefixRegex], removes it.
 */
public fun String.removePrefix(prefixRegex: Regex): String = find(prefixRegex).let { prefix ->
    if (prefix == null || prefix.range.first != 0) this
    else substring(prefix.value.length..<length)
}

/**
 * If this [CharSequence] ends with a subsequence matching [suffixRegex], returns a new char sequence
 * with the suffix removed.
 * Otherwise, returns a new char sequence with the same characters.
 */
public fun CharSequence.removeSuffix(suffixRegex: Regex): CharSequence = findLast(suffixRegex).let { suffix ->
    if (suffix == null || suffix.range.last != lastIndex) subSequence(indices)
    else subSequence(0..<length - suffix.value.length)
}

/**
 * If this [String] ends with a substring matching [suffixRegex], removes it.
 */
public fun String.removeSuffix(suffixRegex: Regex): String = findLast(suffixRegex).let { suffix ->
    if (suffix == null || suffix.range.last != lastIndex) this
    else substring(0..<length - suffix.value.length)
}

/**
 * If this [CharSequence] starts with a subsequence matching
 * [prefixRegex] and ends with a subsequence matching [suffixRegex],
 * returns a new char sequence with the prefix and the suffix removed.
 * Otherwise, returns a new char sequence.
 */
public fun CharSequence.removeSurrounding(prefixRegex: Regex, suffixRegex: Regex): CharSequence {
    val prefix = find(prefixRegex)
    val suffix = findLast(suffixRegex)

    return if (prefix == null || suffix == null || prefix.range.first != 0 || prefix.range.last != lastIndex) subSequence(indices)
    else subSequence(prefix.value.length..<length - suffix.value.length)
}

/**
 * If this [String] starts with a subsequence matching
 * [prefixRegex] and ends with a subsequence matching [suffixRegex],
 * removes them.
 */
public fun String.removeSurrounding(prefixRegex: Regex, suffixRegex: Regex): String {
    val prefix = find(prefixRegex)
    val suffix = findLast(suffixRegex)

    return if (prefix == null || suffix == null || prefix.range.first != 0 || prefix.range.last != lastIndex) this
    else substring(prefix.value.length..<length - suffix.value.length)
}
