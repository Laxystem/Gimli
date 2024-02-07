/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project: Gimli and contributors.
 */

package quest.laxla.gimli.ld

import com.apicatalog.jsonld.JsonLd
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import quest.laxla.gimli.util.LanguageCode
import java.util.*

fun main() {
    val json = JsonLd.expand("https://mastodon.social/users/mastodon").loader(loader).get()!!

    println(json)
    Json.decodeFromString<ImmutableList<LdElement<Any>>>(json.toString())

    val code = "iw-IL"
    println(code)
    val languageCode: LanguageCode = Locale.forLanguageTag(code)
    val encoded = Json.encodeToString(languageCode)
    println(encoded)
    println(Json.decodeFromString<LanguageCode>(encoded))
}