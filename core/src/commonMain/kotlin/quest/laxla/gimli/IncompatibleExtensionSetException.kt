/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli

import quest.laxla.gimli.util.emptyString

/**
 * Signifies an [Extension] is incompatible with another extension, or that one of its requirements isn't fulfilled.
 *
 * @since 0.0.1-alpha.3
 */
public open class IncompatibleExtensionSetException(cause: Throwable? = null, message: String? = null) : Exception(message, cause) {
    /**
     * @since 0.0.1-alpha.3
     */
    public constructor(
        cause: Throwable? = null,
        extensionNamespace: String,
        raisingExtensionNamespace: String? = null,
        message: String? = null
    ) : this(
        cause,
        message = "Extension [$extensionNamespace] is incompatible"
                + (if (raisingExtensionNamespace.isNullOrBlank()) emptyString() else " with [$raisingExtensionNamespace]")
                + if (message.isNullOrBlank()) "." else "; $message"
    )
}
