package com.syc.mvvm.framework.utils

import android.os.Handler
import android.os.Looper

/**
 * 延迟执行
 */
class DelayCall(looper: Looper = Looper.getMainLooper()) {
    private val handler = Handler(looper)

    fun call(delayMills: Long, action: DelayCall.() -> Unit) {
        handler.postDelayed({
            action.invoke(this)
        }, delayMills)
    }

    fun cancel(){
        handler.removeCallbacksAndMessages(null)
    }
}


/**
 * 延迟执行
 */
fun runDelay(delayMillis: Long, block: DelayCall.() -> Unit): DelayCall {
    return DelayCall().apply {
        call(delayMillis,block)
    }
}
