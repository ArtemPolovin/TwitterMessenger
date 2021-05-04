package com.example.domain.common

sealed class Reslt<out T: Any> {
    object Loading: Reslt<Nothing>()
    data class Success<out T: Any>(val data: T): Reslt<T>()
    data class Failure(val error: Throwable? = null, val message: String = ""): Reslt<Nothing>()
}