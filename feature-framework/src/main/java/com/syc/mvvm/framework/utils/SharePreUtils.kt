package com.syc.mvvm.framework.utils

import android.content.Context
import android.content.SharedPreferences
import com.syc.mvvm.framework.base.application
import com.syc.mvvm.framework.json.gson

private const val SP_NAME = "default_shared_preferences"

val sharedPreferences: SharedPreferences by lazy {
    application.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
}


fun Any.saveToSp(key: String) {
    sharedPreferences.edit().apply {
        when (this@saveToSp) {
            is Long -> putLong(key, this@saveToSp)
            is String -> putString(key, this@saveToSp)
            is Int -> putInt(key, this@saveToSp)
            is Boolean -> putBoolean(key, this@saveToSp)
            is Float -> putFloat(key, this@saveToSp)
            else -> gson.toJson(this@saveToSp).apply {
                putString(key, this)
            }
        }
    }.apply()
}

fun <T> getFromSp(key: String, defaultValue: T): T {
    return sharedPreferences.run {
        when (defaultValue) {
            is Long -> getLong(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> getString(key, "").run {
                if (this.isNullOrEmpty()) {
                    defaultValue
                } else {
                    gson.fromJson(this, defaultValue!!::class.java)
                }
            }
        }
    } as T
}