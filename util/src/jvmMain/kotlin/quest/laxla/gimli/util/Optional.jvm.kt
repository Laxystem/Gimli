package quest.laxla.gimli.util

import java.util.Optional as JvOptional

public fun <T> Optional<T>.toJavaOptional(): JvOptional<T> where T : Any = when (this) {
    is Optional.Empty -> JvOptional.empty()
    is Optional.Present -> JvOptional.of(value)
}

public fun <T> JvOptional<T>.toGimliOptional(): Optional<T> where T : Any =
    if (isPresent) Optional of get() else Optional.Empty
