package quest.laxla.gimli.util

import kotlin.jvm.JvmInline

public sealed interface Optional<out T> {

    @JvmInline
    public value class Present<T>(public val value: T) : Optional<T>
    public data object Empty : Optional<Nothing>

    public companion object {
        /**
         * Wraps [value] with an [Optional].
         */
        public infix fun <T> of(value: T): Optional<T> = Present(value)

        /**
         * Wraps [value] with an [Optional], turning `null` into [Empty].
         */
        public infix fun <T> ofNullable(value: T?): Optional<T> where T : Any =
            if (value == null) Empty else Optional of value

        /**
         * Wraps this with an [Optional].
         */
        public fun <T> T.asOptional(): Optional<T> = Optional of this

        /**
         * Wraps this with an [Optional], returning [Empty] if `null`.
         */
        public fun <T> T?.toOptional(): Optional<T> where T : Any = Optional ofNullable this

        /**
         * Returns the value of [Optional], or throws.
         */
        public val <T> Optional<T>.value: T
            get() = if (this is Present<T>) value else throw IllegalStateException("Optional is empty")

        /**
         * Returns the value of [Optional], or `null` if [Empty].
         */
        public val <T> Optional<T>.valueOrNull: T? where T : Any
            get() = if (this is Present<T>) value else null
    }
}