package com.example.core

sealed class DataStatus<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null

) {
    enum class Status{
        SUCCESS, ERROR, EMPTY
    }
    class Success<T>(data: T) : DataStatus<T>(Status.SUCCESS, data = data)
    class Error<T>(errorMessage: String) : DataStatus<T>(Status.ERROR, message = errorMessage)
    class Empty<T> : DataStatus<T>(Status.EMPTY)
}
