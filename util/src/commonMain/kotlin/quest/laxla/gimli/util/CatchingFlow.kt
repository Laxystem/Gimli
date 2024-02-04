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
public typealias CatchingFlow<T> = Flow<Result<T>>
public typealias CatchingFlowCollector<T> = FlowCollector<Result<T>>

private val DefaultFailureHandlingLevel = Level.WARN

/**
 * Logs then discards failures, unboxing successes.
 *
 * @see filterFailures
 * @see Result
 */
public fun <T> CatchingFlow<T>.handleFailures(logger: KLogger, level: Level = DefaultFailureHandlingLevel): Flow<T> =
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
public fun <T> CatchingFlow<T>.filterFailures(): Flow<T> = transform { result -> result.onSuccess { emit(it) } }

/**
 * Creates a [CatchingFlow], catching any exceptions thrown and adding them to the result.
 */
public inline fun <R> flowCatching(crossinline block: suspend CatchingFlowCollector<R>.() -> Unit): CatchingFlow<R> =
    flow {
        try {
            block()
        } catch (e: Throwable) {
            emit(e)
        }
    }


/**
 * [Emit][FlowCollector.emit]s a value, wrapping it with a successful [Result].
 */
public suspend fun <T> CatchingFlowCollector<T>.emit(value: T) {
    emit(Result.success(value))
}

/**
 * [Emit][FlowCollector.emit] a failure.
 */
public suspend fun <T> CatchingFlowCollector<T>.emit(exception: Throwable) {
    emit(Result.failure(exception))
}

/**
 * Creates a [CatchingFlow] that produces values from the specified `vararg`-arguments.
 */
public fun <T> catchingFlowOf(vararg elements: T): CatchingFlow<T> = flow {
    for (element in elements) {
        emit(element)
    }
}

/**
 * Creates a [CatchingFlow] that produces [failures] from the specified `vararg`-arguments.
 */
public fun <T> catchingFlowOf(vararg failures: Throwable): CatchingFlow<T> = flow {
    for (failure in failures) {
        emit(failure)
    }
}

/**
 * Creates a [CatchingFlow] that produces the given [value].
 */
public fun <T> catchingFlowOf(value: T): CatchingFlow<T> = flow { emit(value) }

/**
 * Creates a [CatchingFlow] that produces the given [failure].
 */
public fun <T> catchingFlowOf(failure: Throwable): CatchingFlow<T> = flow {
    emit(failure)
}

/**
 * Turns this [Flow] into a [CatchingFlow].
 */
public fun <T> Flow<T>.catching(): Flow<Result<T>> = map(Result.Companion::success)

public fun <T> (() -> T).asFlowCatching(): Flow<Result<T>> = flowCatching {
    emit(invoke())
}

public fun <T> (suspend () -> T).asFlowCatching(): Flow<Result<T>> = flowCatching {
    emit(invoke())
}

@JvmName(name = "iterableToFlowCatching")
public fun <T> Iterable<T>.asFlowCatching(): Flow<Result<T>> = flow {
    forEach {
        emit(it)
    }
}

@JvmName(name = "iterableOfProvidersToFlowCatching()")
public fun <T> Iterable<() -> T>.asFlowCatching(): Flow<Result<T>> = flow {
    forEach {
        emit(kotlin.runCatching { it.invoke() })
    }
}

@JvmName(name = "iterableOfSuspendingProvidersToFlowCatching()")
public fun <T> Iterable<suspend () -> T>.asFlowCatching(): Flow<Result<T>> = flow {
    forEach {
        emit(kotlin.runCatching { it.invoke() })
    }
}
