/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline

/**
 * Represents a percentage between 0% and 100%.
 *
 * @property asFraction The value of this percentage as a [Float] between `0` and `1`.
 */
@JvmInline
@Serializable(with = Percentage.Companion::class)
public value class Percentage private constructor(public val asFraction: Float) : Comparable<Percentage> {
    /**
     * The value of this percentage as an [Int] between `0` and `100`.
     */
    public val asInt: Int get() = (asFraction * 100).toInt()

    init {
        if (asFraction !in 0f..1f) throw ArithmeticException("Percentage is $this, should be 0%..100%.")
    }

    public operator fun times(other: Int): Percentage = (asFraction * other).toPercentage()

    public operator fun plus(other: Percentage): Percentage = (asFraction + other.asFraction).toPercentage()
    public operator fun minus(other: Percentage): Percentage = (asFraction - other.asFraction).toPercentage()

    public operator fun times(other: Percentage): Percentage = (asFraction * other.asFraction).toPercentage()
    public operator fun div(other: Percentage): Percentage = (asFraction / other.asFraction).toPercentage()

    override fun compareTo(other: Percentage): Int = asFraction.compareTo(other.asFraction)

    override fun toString(): String = "${asFraction * 100}%"

    public companion object : KSerializer<Percentage> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(serialName = "quest.laxla.gimli.util.Percentage", PrimitiveKind.FLOAT)

        public val Zero: Percentage get() = 0f.toPercentage()
        public val Hundred: Percentage get() = 1f.toPercentage()

        /**
         * Creates a [Percentage] from an [Int] between 0 and 100.
         */
        public val Int.percent: Percentage get() = Percentage(asFraction = this / 100f)

        /**
         * Creates a [Percentage] from a [Float] between 0 and 1.
         */
        public fun Float.toPercentage(): Percentage = Percentage(asFraction = this)

        override fun deserialize(decoder: Decoder): Percentage = decoder.decodeFloat().toPercentage()

        override fun serialize(encoder: Encoder, value: Percentage) { encoder.encodeFloat(value.asFraction) }
    }
}
