/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright (C) 2024 Project Gimli and Contributors.
 */

package quest.laxla.gimli.util

/**
 * Represents a [Boolean] whose value may not be known.
 */
public typealias Maybe = Boolean?

public val Unknown: Maybe get() = null

public val Maybe.isTrue: Boolean get() = this == true
public val Maybe.isFalse: Boolean get() = this == false
public val Maybe.isNotTrue: Boolean get() = this != true
public val Maybe.isNotFalse: Boolean get() = this != false
public val Maybe.isKnown: Boolean get() = this != Unknown
public val Maybe.isUnknown: Boolean get() = this == Unknown

public operator fun Maybe.not(): Maybe = this?.let(Boolean::not)

public infix fun Maybe.or(other: Maybe): Maybe {
    this ?: return Unknown
    other ?: return Unknown

    return this || other
}

public infix fun Maybe.and(other: Maybe): Maybe {
    this ?: return Unknown
    other ?: return Unknown

    return this && other
}

public infix fun Maybe.xor(other: Maybe): Maybe {
    this ?: return Unknown
    other ?: return Unknown

    return this xor other
}

public infix fun Maybe.implies(other: Maybe): Maybe {
    this ?: return Unknown
    other ?: return Unknown


    return this implies other
}
