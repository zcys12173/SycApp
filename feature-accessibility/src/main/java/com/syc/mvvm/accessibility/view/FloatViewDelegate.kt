package com.syc.mvvm.accessibility.view

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat.RGBA_8888
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.syc.mvvm.accessibility.R
import com.syc.mvvm.framework.utils.AppForegroundChangedListener
import com.syc.mvvm.framework.utils.addForegroundChangedListener
import com.syc.mvvm.framework.utils.removeForegroundChangedListener
import com.syc.mvvm.permission.hasDrawOverlaysPermission
import com.syc.mvvm.permission.requestDrawOverlaysPermission

/**
 * create by syc, 2022/9/30
 */
class FloatViewDelegate(private val context: Context) {
    private val application = context.applicationContext as Application

    private val wm by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val layoutParams by lazy {
        WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = RGBA_8888
            height = 80
            width = 80
            gravity = Gravity.START or Gravity.TOP
        }
    }

    private val floatView by lazy {
        LayoutInflater.from(context).inflate(R.layout.layout_float_view, null)
            .apply {
                setOnTouchListener(FloatTouchListener())
                setOnClickListener {

                }
            }
    }

    private val foregroundChangedListener:AppForegroundChangedListener = { it: Boolean ->
        if (it) {
            Log.e("sss", "add float view by app Foreground")
            showFloatView()
        } else {
            Log.e("sss", "remove float view by app background")
//            closeFloatView()
        }
    }

    init {
        application.addForegroundChangedListener(foregroundChangedListener)
    }

    fun release() {
        closeFloatView()
        application.removeForegroundChangedListener(foregroundChangedListener)
    }

    private var isFloatViewVisible = false

    fun showFloatView() {
        if (isFloatViewVisible) {
            return
        }
        if (!context.hasDrawOverlaysPermission()) {
            context.requestDrawOverlaysPermission()
            return
        }
        isFloatViewVisible = true
        wm.addView(floatView, layoutParams)
    }

    private fun closeFloatView() {
        if (!isFloatViewVisible) {
            return
        }
        isFloatViewVisible = false
        wm.removeView(floatView)
    }


    inner class FloatTouchListener : View.OnTouchListener {
        private var x = 0f
        private var y = 0f
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX
                    y = event.rawY
                }
//                MotionEvent.ACTION_UP -> {
//                    v?.performClick()
//                }

                MotionEvent.ACTION_MOVE -> {
                    val curX = event.rawX
                    val curY = event.rawY
                    val newX = curX - x
                    val newY = curY - y
                    x = curX
                    y = curY
                    layoutParams.x += newX.toInt()
                    layoutParams.y += newY.toInt()
                    wm.updateViewLayout(floatView, layoutParams)
                }
            }
            return false
        }
    }
}