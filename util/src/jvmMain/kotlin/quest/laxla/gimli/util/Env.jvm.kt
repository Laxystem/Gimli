/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.util

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.io.File
import java.util.*

private val envVariableFiles = persistentListOf(".env", "local.properties")

public actual fun fetchEnvironmentVariables(
    defaults: Map<String, String>
): ImmutableMap<String, String> {
    val properties = Properties(System.getProperties())
    properties.putAll(defaults)

    envVariableFiles.forEach { filename ->
        File(filename).inputStream().use {
            properties.load(it)
        }
    }

    return properties.map { (key, value) -> key.toString() to value.toString() }.toTypedArray().let(::persistentMapOf)
}

