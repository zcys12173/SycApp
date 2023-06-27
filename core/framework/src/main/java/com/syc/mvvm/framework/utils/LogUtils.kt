package com.syc.mvvm.framework.utils

import android.util.Log

enum class LogLevel {
    DEBUG,INFO,WARN,ERROR
}

fun log(tag: String, text: String, level: LogLevel = LogLevel.DEBUG) {
    when (level) {
        LogLevel.DEBUG -> Log.d(tag, text)
        LogLevel.INFO -> Log.i(tag, text)
        LogLevel.WARN -> Log.w(tag, text)
        LogLevel.ERROR -> Log.e(tag, text)
    }
}

fun Any.log(text: String,level: LogLevel = LogLevel.INFO) {
    log(this.javaClass.simpleName, text,level)
}