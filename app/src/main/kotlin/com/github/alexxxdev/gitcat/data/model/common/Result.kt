package com.github.alexxxdev.gitcat.data.model.common

sealed class Result<out V : Any> {
    abstract fun value(): V?
    abstract fun error(): Error?

    inline fun <X> fold(success: (V) -> X, failure: (Error) -> X): X = when (this) {
        is Success -> success(this.value)
        is Failure -> failure(this.error)
    }

    class Success<out V : Any>(val value: V) : Result<V>() {
        override fun toString() = "[Success: $value]"
        override fun value(): V? = value
        override fun error(): Error? = null
    }

    class Failure<out V : Any>(val error: Error) : Result<V>() {
        override fun toString() = "[Failure: $error]"
        override fun value(): V? = null
        override fun error(): Error? = error
    }

    companion object {
        fun error(ex: Error) = Failure<Nothing>(ex)

        fun <V : Any> of(value: V?, fail: (() -> Exception) = { Exception() }): Result<V> = value?.let { Success(it) }
                ?: error(fail())

        fun <V : Any> of(f: () -> V): Result<V> = try {
            Success(f())
        } catch (ex: Exception) {
            Failure(Error.of(ex))
        }
    }
}

fun <V : Any> Result<V>.success(f: (V) -> Unit) = fold(f, {})

fun <V : Any> Result<V>.failure(f: (Error) -> Unit) = fold({}, f)