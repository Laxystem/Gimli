/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

/**
 * Signifies this operation is only supported during initialization.
 */
public open class UnsupportedDynamicallyException(message: String? = null, cause: Throwable? = null) : UnsupportedOperationException(message, cause)