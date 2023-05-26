package com.syc.mvvm.accessibility.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.syc.mvvm.accessibility.utils.findButton
import com.syc.mvvm.accessibility.view.FloatViewDelegate

class MyAccessibilityService:AccessibilityService() {
    private val floatViewDelegate by lazy {  FloatViewDelegate(this) }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        floatViewDelegate.showFloatView()
    }


    override fun onDestroy() {
        super.onDestroy()
        floatViewDelegate.release()
    }

}