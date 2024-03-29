/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.ld

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import quest.laxla.gimli.util.TextDirection
import quest.laxla.gimli.ld.property.LdpID
import quest.laxla.gimli.ld.property.LdpType
import quest.laxla.gimli.util.IetfBcp47

typealias Iri = String // TODO
typealias LdType = String // TODO

typealias LdObject = LdElement<Nothing>
typealias LdProperty<T> = ImmutableList<LdElement<T>>
typealias LdObjectProperty = ImmutableList<LdObject>

@Serializable(with = LdElementSerializer::class)
interface LdElement<out T> : LdpType, LdpID where T : Any {
    @SerialName("@value")
    val value: T?

    @SerialName("@language")
    val language: IetfBcp47?

    @SerialName("@direction")
    val textDirection: TextDirection?
}
