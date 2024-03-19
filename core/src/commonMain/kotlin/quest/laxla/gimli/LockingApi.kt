/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

/**
 * Signifies this function or setter locks a resource,
 * preventing concurrent usage.
 *
 * Only use such functions when absolutely needed.
 */
@MustBeDocumented
@RequiresOptIn(message = "Prevents concurrent use of a resource.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_SETTER)
public annotation class LockingApi(val replaceWith: ReplaceWith = ReplaceWith(expression = ""))
