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
