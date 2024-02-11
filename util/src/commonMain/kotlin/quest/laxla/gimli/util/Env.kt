/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

package quest.laxla.gimli.util

import kotlin.properties.ReadOnlyProperty

private const val lowercase = "lowercase"
private const val uppercase = "uppercase"
private val uppercaseRegex = "(?<$lowercase>[a-z])(?<$uppercase>[A-Z])".toRegex()


/**
 * Environment variables and program arguments.
 *
 * @author Laxystem
 */
public var environment: ImmutableMap<String, String> = fetchEnvironmentVariables(emptyPersistentMap())
    private set

/**
 * Gets the env variable named the same as the property in `SCREAMING_SNAKE_CASE`.
 *
 * @see environment
 */
public val env: ReadOnlyProperty<Nothing?, String?> = ReadOnlyProperty { _, property ->
    environment[property.name.replace(uppercaseRegex) {
        it.groups[lowercase]!!.value + '_' + it.groups[uppercase]!!.value
    }.uppercase()]
}

/**
 * Gets the env variable named the same as the property in `SCREAMING_SNAKE_CASE`, defaulting to [default].
 *
 * @see environment
 */
public fun env(default: String): ReadOnlyProperty<Nothing?, String> = ReadOnlyProperty { thisRef, property ->
    env.getValue(thisRef, property) ?: default
}

/**
 * Update [environment] and all properties delegating to it directly or via [env].
 */
public fun updateEnvironmentVariables() {
    environment = fetchEnvironmentVariables(environment)
}

/**
 * Fetch the current env variables, defaulting to [defaults].
 */
public expect fun fetchEnvironmentVariables(defaults: Map<String, String>): ImmutableMap<String, String>
