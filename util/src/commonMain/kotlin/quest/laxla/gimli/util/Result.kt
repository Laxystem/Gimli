package quest.laxla.gimli.util

public operator fun <T> Throwable.not(): Result<T> = Result.failure(exception = this)
