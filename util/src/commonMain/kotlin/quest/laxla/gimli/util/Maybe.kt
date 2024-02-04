package quest.laxla.gimli.util

typealias Maybe = Boolean?

val Unknown: Maybe = null

val Maybe.isTrue get() = this == true
val Maybe.isFalse get() = this == false
val Maybe.isNotTrue get() = this == true
val Maybe.isNotFalse get() = this != false
val Maybe.isKnown get() = this != Unknown
val Maybe.isUnknown get() = this == Unknown
