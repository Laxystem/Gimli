/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

import java.util.Optional as JvOptional

public fun <T> Optional<T>.toJavaOptional(): JvOptional<T> where T : Any = when (this) {
    is Optional.Empty -> JvOptional.empty()
    is Optional.Present -> JvOptional.of(value)
}

public fun <T> JvOptional<T>.toGimliOptional(): Optional<T> where T : Any =
    if (isPresent) Optional of get() else Optional.Empty
