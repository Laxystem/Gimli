package quest.laxla.gimli.util

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.Level
import kotlinx.coroutines.flow.*
import kotlin.jvm.JvmName

/**
 * An asynchronous data stream capable of storing failures.
 *
 * @see Flow
 * @see Result
 */
typealias CatchingFlow<T> = Flow<Result<T>>
typealias CatchingFlowCollector<T> = FlowCollector<Result<T>>

val DefaultFailureHandlingLevel = Level.WARN

/**
 * Logs then discards failures, unboxing successes.
 *
 * @see filterFailures
 * @see Result
 */
fun <T> CatchingFlow<T>.handleFailures(logger: KLogger, level: Level = DefaultFailureHandlingLevel) =
    transform { result ->
        result.fold(
            onSuccess = { emit(it) },
            onFailure = {
                logger.at(level) {
                    cause = it
                    message = "Fetch failed"
                }
            }
        )
    }

/**
 * Discards failures, unboxing successes.
 *
 * @see handleFailures
 * @see Result
 */
fun <T> CatchingFlow<T>.filterFailures() = transform { result -> result.onSuccess { emit(it) } }

/**
 * Creates a [CatchingFlow], catching any exceptions thrown and adding them to the result.
 */
inline fun <R> flowCatching(crossinline block: suspend CatchingFlowCollector<R>.() -> Unit): CatchingFlow<R> = flow {
    try {
        block()
    } catch (e: Throwable) {
        emit(e)
    }
}



/**
 * [Emit][FlowCollector.emit]s a value, wrapping it with a successful [Result].
 */
suspend fun <T> CatchingFlowCollector<T>.emit(value: T) = emit(Result.success(value))

/**
 * [Emit][FlowCollector.emit] a failure.
 */
suspend fun <T> CatchingFlowCollector<T>.emit(exception: Throwable) = emit(Result.failure(exception))

/**
 * Creates a [CatchingFlow] that produces values from the specified `vararg`-arguments.
 */
fun <T> catchingFlowOf(vararg elements: T): CatchingFlow<T> = flowCatching {
    for (element in elements) {
        emit(element)
    }
}

/**
 * Creates a [CatchingFlow] that produces [failures] from the specified `vararg`-arguments.
 */
fun <T> catchingFlowOf(vararg failures: Throwable): CatchingFlow<T> = flowCatching {
    for (element in failures) {
        emit(element)
    }
}

/**
 * Creates a [CatchingFlow] that produces the given [value].
 */
fun <T> catchingFlowOf(value: T): CatchingFlow<T> = flowCatching {
    emit(value)
}

/**
 * Creates a [CatchingFlow] that produces the given [failure].
 */
fun <T> catchingFlowOf(failure: Throwable): CatchingFlow<T> = flowCatching {
    emit(failure)
}

/**
 * Turns this [Flow] into a [CatchingFlow].
 */
fun <T> Flow<T>.catching() = map(Result.Companion::success)

fun <T> (() -> T).asFlowCatching() = flowCatching {
    emit(invoke())
}

fun <T> (suspend () -> T).asFlowCatching() = flowCatching {
    emit(invoke())
}

@JvmName(name = "iterableToFlowCatching")
fun <T> Iterable<T>.asFlowCatching() = flowCatching {
    forEach {
        emit(it)
    }
}

@JvmName(name = "iterableOfProvidersToFlowCatching()")
fun <T> Iterable<() -> T>.asFlowCatching() = flowCatching {
    forEach {
        emit(kotlin.runCatching { it.invoke() })
    }
}

@JvmName(name = "iterableOfSuspendingProvidersToFlowCatching()")
fun <T> Iterable<suspend () -> T>.asFlowCatching() = flowCatching {
    forEach {
        emit(kotlin.runCatching { it.invoke() })
    }
}
