/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.ld.activity

import quest.laxla.gimli.ld.Iri

interface Type {
    val name: Iri

    companion object {
        operator fun invoke(name: Iri) = object : Type {
            override val name: Iri get() = name
        }
    }
}

fun String.toType() = Type(name = this)
fun String.toActivityStreamsType() = Type(name = ActivityStreams + this)
