package quest.laxla.gimli.util

const val Ellipsis = '…'
const val DotDotDot = "..."
const val QuoteDotDotDot = "[$DotDotDot]"
const val QuoteEllipsis = "[$Ellipsis]"
const val Empty = ""

fun String?.toStringOrEmpty() = this ?: Empty
fun Char?.toStringOrEmpty() = this?.toString() ?: Empty

fun String.trimmedAfter(maximumLength: Int, replaceWith: Char? = null) = trimmedAfter(maximumLength, replaceWith.toStringOrEmpty())

fun String.trimmedAfter(maximumLength: Int, replaceWith: String = Empty) = if (length > maximumLength) {
    substring(range = 0..<maximumLength - replaceWith.length) + replaceWith
} else this

inline fun String.ifNotEmpty(block: (String) -> Unit) {
    if (isNotEmpty()) block(this)
}

fun emptyString() = ""