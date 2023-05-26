package com.syc.mvvm.accessibility.utils

import android.view.accessibility.AccessibilityNodeInfo

fun AccessibilityNodeInfo.findButton(text:String):AccessibilityNodeInfo?{
    for(i in 0 until childCount){
        val node = getChild(i)
        if((node.text == text) && node.isClickable){
            return node
        }
        val result = node.findButton(text)
        if(result != null){
            return result
        }
    }
    return null
}