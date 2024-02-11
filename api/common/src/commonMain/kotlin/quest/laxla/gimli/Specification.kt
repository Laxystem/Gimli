/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

public enum class Specification(
    public val context: String,
    public val baseUrl: String = "$context#"
) {
    ActivityPub(context = "https://www.w3.org/ns/activitystreams"),
    ForgeFed(context = "https://forgefed.org/ns"),
    Gimli(context = "https://gimli.laxla.quest/ns"); // TODO: actually upload something there

    /**
     * Marks this property or class as complying to a specification.
     *
     * This annotation is non-exclusionary.
     */
    @MustBeDocumented
    @Repeatable
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
    public annotation class Compliance(
        val specification: Specification,
        val name: String,
        val level: Level = Level.Complete
    ) {
        public enum class Level {
            Complete, Partial, Planned
        }
    }
}
