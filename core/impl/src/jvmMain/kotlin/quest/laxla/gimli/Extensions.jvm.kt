/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import java.io.File

public actual val extensions: ImmutableSet<Extension> by lazy(::discoverExtensions)

private fun discoverExtensions(): ImmutableSet<Extension> =
    ClassLoader.getSystemResources("META-INF/quest/laxla/gimli/extensions")
        .asSequence()
        .map { File(it.toURI()) }
        .filter(File::isDirectory)
        .flatMap { it.listFiles { file -> file.extension == "classname" }?.asSequence() ?: emptySequence() }
        .map(File::nameWithoutExtension)
        .distinct()
        .map { ClassLoader.getSystemClassLoader().loadClass(it).kotlin.objectInstance }
        .filterNotNull()
        .filterIsInstance<Extension>()
        .toImmutableSet()
