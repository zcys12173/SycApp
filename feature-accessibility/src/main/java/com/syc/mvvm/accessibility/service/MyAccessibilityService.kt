package com.syc.mvvm.accessibility.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.syc.mvvm.accessibility.AccessibilityManager
import com.syc.mvvm.accessibility.utils.isAccessibilityEnabled
import com.syc.mvvm.accessibility.view.FloatViewDelegate

class MyAccessibilityService : AccessibilityService() {
    private val floatViewDelegate by lazy { FloatViewDelegate(this) }

    override fun onCreate() {
        super.onCreate()
        AccessibilityManager.notify {
            it.onCreate()
        }

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        AccessibilityManager.notify {
            it.onAccessibilityEvent(event, rootInActiveWindow,this)
        }
    }

    override fun onInterrupt() {
        AccessibilityManager.notify {
            it.onInterrupt()
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        isAccessibilityEnabled = true
        floatViewDelegate.showFloatView()
        AccessibilityManager.notify {
            it.onServiceConnected()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        floatViewDelegate.release()
        AccessibilityManager.notify {
            it.onDestroy()
        }
    }

}