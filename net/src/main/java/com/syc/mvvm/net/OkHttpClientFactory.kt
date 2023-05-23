package com.syc.mvvm.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun createOkHttpClient(loggable: Boolean = true): OkHttpClient {
    return OkHttpClient.Builder().run {
        if (loggable) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logInterceptor)
        }
        build()
    }
}