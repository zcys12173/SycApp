package com.syc.mvvm.framework.utils

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 延迟执行
 */
class DelayCall(lifecycle: Lifecycle? = null, looper: Looper = Looper.getMainLooper()):LifecycleObserver {
    private val handler = Handler(looper)
    init {
        lifecycle?.addObserver(this)
    }

    fun call(delayMills: Long, action: DelayCall.() -> Unit) {
        handler.postDelayed({
            action.invoke(this)
        }, delayMills)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancel(){
        log("cancel call")
        handler.removeCallbacksAndMessages(null)
    }
}


/**
 * 延迟执行
 */
fun runDelay(delayMillis: Long, lifecycle: Lifecycle?=null,block: DelayCall.() -> Unit): DelayCall {
    return DelayCall(lifecycle).apply {
        call(delayMillis,block)
    }
}
