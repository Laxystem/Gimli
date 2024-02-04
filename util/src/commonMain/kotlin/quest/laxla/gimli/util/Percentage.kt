package quest.laxla.gimli.util

/**
 * @property asFraction The value of this percentage as a [Float] between `0` and `1`.
 */
@JvmInline
value class Percentage private constructor(val asFraction: Float) {
    /**
     * The value of this percentage as an [Int] between `0` and `100`.
     */
    val asInt get() = (asFraction * 100).toInt()

    init {
        require(asFraction in 0f..1f) { "Percentage must be between 0% and 100%." }
    }

    override fun toString(): String = "$asInt%"

    companion object {
        /**
         * Creates a [Percentage] from an [Int] between 0 and 100.
         */
        val Int.percent get() = Percentage(asFraction = this / 100f)

        /**
         * Creates a [Percentage] from a [Float] between 0 and 1.
         */
        fun Float.toPercentage() = Percentage(asFraction = this)
    }
}
