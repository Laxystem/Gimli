/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

import kotlin.reflect.KClass

@MustBeDocumented
@RequiresOptIn(message = "This function is meant to only be callable by a specific type. Make sure you know what you're doing.")
@Target(AnnotationTarget.FUNCTION)
public annotation class OnlyCallableBy(val klass: KClass<*>)
