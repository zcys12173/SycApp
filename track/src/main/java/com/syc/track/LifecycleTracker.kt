@file:JvmName("LifecycleTracker")

package com.syc.track

import android.app.Activity
import android.util.Log


object LifecycleTracker {
    private val cacheMap = mutableMapOf<String, Long>()


    fun onStart(obj: Any) {
        if (obj is Activity) {
            cacheMap[obj.javaClass.simpleName] = System.currentTimeMillis()
        }
    }


    fun onStop(obj: Any) {
        if (obj is Activity) {
            obj.javaClass.simpleName.let {key->
                cacheMap.remove(key)?.run{
                    upload(key, System.currentTimeMillis() - this)
                }
            }

        }
    }

    private fun upload(name: String, duration: Long) {
        Log.e("LifecycleTracker", "统计到页面$name 停留时长为$duration 毫秒")
    }
}