/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and contributors.
 */

@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package quest.laxla.gimli.util

public actual typealias Locale = java.util.Locale

public actual fun String.toLocale(): Locale = java.util.Locale.forLanguageTag(this)

public actual val CurrentLocale: Locale get() = java.util.Locale.getDefault()

public actual val Locale.formatted: String get() = displayName
public actual val Locale.code: String get() = toLanguageTag()
public actual val Locale.formattedLanguage: String get() = displayLanguage
public actual val Locale.languageCode: String get() = language
public actual val Locale.formattedScript: String get() = displayScript
public actual val Locale.scriptCode: String get() = script
public actual val Locale.formattedRegion: String get() = displayCountry
public actual val Locale.regionCode: String get() = country
