package quest.laxla.gimli.util

public const val Ellipsis: Char = '…'
public const val DotDotDot: String = "..."
public const val QuoteDotDotDot: String = "[$DotDotDot]"
public const val QuoteEllipsis: String = "[$Ellipsis]"

public fun String?.toStringOrEmpty(): String = this ?: emptyString()
public fun Char?.toStringOrEmpty(): String = this?.toString() ?: emptyString()

public fun String.trimmedAfter(maximumLength: Int, replaceWith: Char? = null): String = trimmedAfter(maximumLength, replaceWith.toStringOrEmpty())

public fun String.trimmedAfter(maximumLength: Int, replaceWith: String = emptyString()): String = if (length > maximumLength) {
    substring(range = 0..<maximumLength - replaceWith.length) + replaceWith
} else this

public inline fun String.ifNotBlank(block: (String) -> Unit) {
    if (isNotBlank()) block(this)
}

public inline fun String.ifBlank(block: (String) -> Unit) {
    if (isBlank()) block(this)
}

public fun emptyString(): String = ""