/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package quest.laxla.gimli.util

import kotlinx.browser.window

public external object Intl {
    public class Locale(baseName: String) {
        internal val language: String
        internal val baseName: String
        internal val script: String
        internal val region: String
    }
}

public actual typealias Locale = Intl.Locale

public actual fun String.toLocale(): Locale = Locale(baseName = this)

public actual val CurrentLocale: Locale get() = Locale(window.navigator.language)

public actual val Locale.formatted: String get() = baseName
public actual val Locale.code: String get() = baseName
public actual val Locale.formattedLanguage: String get() = language
public actual val Locale.languageCode: String get() = language
public actual val Locale.formattedScript: String get() = script
public actual val Locale.scriptCode: String get() = script
public actual val Locale.formattedRegion: String get() = region
public actual val Locale.regionCode: String get() = region
