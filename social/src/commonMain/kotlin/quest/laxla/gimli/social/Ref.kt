/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.social

import com.eygraber.uri.Uri
import quest.laxla.gimli.social.Ref.*
import kotlin.jvm.JvmInline

/**
 * References [Element]s [directly][Direct] or [via federal identifier][Federal].
 */
public sealed interface Ref<T> : Identified where T : Element<T> { // TODO: serialize
    @JvmInline
    public value class Direct<T>(public val value: T) : Ref<T> where T : Element<T> {
        override val primaryFederalIdentifier: Uri get() = value.primaryFederalIdentifier
    }

    @JvmInline
    public value class Federal<T>(override val primaryFederalIdentifier: Uri) :
        Ref<T> where T : Element.Federalized<T>

    public companion object {
        /**
         * Wrap this as a direct reference.
         */
        public val <T> T.ref: Direct<T> where T : Element<T> get() = Direct(value = this)
    }
}
