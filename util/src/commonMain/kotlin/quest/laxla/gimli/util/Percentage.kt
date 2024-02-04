package quest.laxla.gimli.util

import kotlin.jvm.JvmInline

/**
 * @property asFraction The value of this percentage as a [Float] between `0` and `1`.
 */
@JvmInline
public value class Percentage private constructor(public val asFraction: Float) {
    /**
     * The value of this percentage as an [Int] between `0` and `100`.
     */
    public val asInt: Int get() = (asFraction * 100).toInt()

    init {
        require(asFraction in 0f..1f) { "Percentage must be between 0% and 100%." }
    }

    override fun toString(): String = "${(asFraction * 100)}%"

    public companion object {
        /**
         * Creates a [Percentage] from an [Int] between 0 and 100.
         */
        public val Int.percent: Percentage get() = Percentage(asFraction = this / 100f)

        /**
         * Creates a [Percentage] from a [Float] between 0 and 1.
         */
        public fun Float.toPercentage(): Percentage = Percentage(asFraction = this)
    }
}
