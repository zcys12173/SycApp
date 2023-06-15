package com.syc.mvvm.net

import okio.IOException

data class HttpEntity<T>(val code: Int, val data: T?, val message: String)


class ServerResponseException(
    val code: Int,
    message: String? = null,
    cause: Throwable? = null
) : IOException(message,cause)