/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.server.networking.webfinger

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import quest.laxla.gimli.server.impl.ProfileProvider
import quest.laxla.gimli.util.GimliJson
import quest.laxla.gimli.util.ImmutableList
import quest.laxla.gimli.util.emptyPersistentList

val contentType = ContentType.parse(value = "application/jrd+json")

fun Application.webfinger() {
    install(Resources)
    install(ContentNegotiation) {
        json(GimliJson, contentType = contentType)
    }

    routing {
        get<WebfingerRequest> {
            ProfileProvider[it.resource]

            call.respond(JsonResourceDescriptor(it.resource))
        }
    }
}

@Resource(path = "/.well-known/webfinger")
class WebfingerRequest(
    val resource: String,
    val rel: ImmutableList<String> = emptyPersistentList()
)
