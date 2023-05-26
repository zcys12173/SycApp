package com.syc.mvvm.framework.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.syc.mvvm.framework.base.application

/**
 * 延迟执行
 */
fun runDelay(delayMillis: Long, block: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(block, delayMillis)
}

/**
 * 显示Toast
 */
fun showToast(text: String, @BaseTransientBottomBar.Duration duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(application, text, duration).show()
}