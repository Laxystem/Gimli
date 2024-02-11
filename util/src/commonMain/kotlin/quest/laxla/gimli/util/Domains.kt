/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.util

private val IriPrefixRegex: Regex = "(web\\+)?[a-z]+:(//)?".toRegex()
private val PathStarterRegex: Regex = "[#/?:]".toRegex()

public infix fun String.isOfDomain(domain: String): Boolean = domain.extractDomain() ==
        if (startsWith(prefix = "acct:", ignoreCase = true)) substringAfter(delimiter = '@').lowercase()
        else extractDomain()


public fun String.extractDomain(): String =
    lowercase().removePrefix(IriPrefixRegex).substringBefore(PathStarterRegex)