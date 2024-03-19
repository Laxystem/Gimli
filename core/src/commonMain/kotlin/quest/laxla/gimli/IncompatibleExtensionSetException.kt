/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

/**
 * Signifies an [Extension] is incompatible with another extension, or that one of its requirements isn't fulfilled.
 *
 * @since 0.0.1-alpha.3
 */
public open class IncompatibleExtensionSetException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)