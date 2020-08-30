package com.syc.router

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

internal class RouterBuilder(val scheme:String){
    val bundle by lazy { Bundle() }

    var callback:((obj:Any)->Unit)? = null
        private set

    fun with(key:String,value:Any) = this.apply {
        when(value){
            is String -> bundle.putString(key,value)
            is Int -> bundle.putInt(key,value)
            is Float -> bundle.putFloat(key,value)
            is Long -> bundle.putLong(key,value)
            is Double -> bundle.putDouble(key,value)
            is Boolean -> bundle.putBoolean(key,value)
            is Parcelable -> bundle.putParcelable(key,value)
            is Serializable -> bundle.putSerializable(key,value)
        }
    }

    fun callback(callback:(obj:Any)->Unit) = this.apply { this.callback = callback }

}