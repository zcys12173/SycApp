package com.syc.mvvm.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

object AccessibilityManager {
    private val listeners = mutableListOf<AccessibilityListener>()
    internal var canNotify = false
        set(value) {
            field = value
            if(!value){
                listeners.forEach {
                    it.onStop()
                }
            }
        }

    fun registerListener(listener: AccessibilityListener){
        if(!listeners.contains(listener)){
            listeners.add(listener)
        }
    }

    fun unregisterListener(listener: AccessibilityListener){
        listeners.remove(listener)
    }

    fun notify(action:(AccessibilityListener)->Unit){
        if(canNotify){
            listeners.forEach(action)
        }
    }
}


interface AccessibilityListener {

    fun onCreate(){}

    fun onAccessibilityEvent(event: AccessibilityEvent?,rootInActiveWindow: AccessibilityNodeInfo?,service: AccessibilityService){}

    fun onInterrupt(){}

    fun onServiceConnected(){}

    fun onDestroy(){}

    fun onStop(){}
}