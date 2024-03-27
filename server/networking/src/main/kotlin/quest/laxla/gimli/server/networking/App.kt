/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.server.networking

import io.ktor.http.*

fun main(vararg urls: String) {

    println(urls.asSequence().map { it to Url(it) }.toMap())

    /*embeddedServer(Netty, port = 8080) {
        webfinger()
    }.start(wait = true)*/
}
