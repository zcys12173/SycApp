package com.syc.mvvm.accessibility.utils

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import com.syc.mvvm.framework.utils.log


/**
 * 通过模拟手势实现点击
 */
fun AccessibilityService.performClick(node: AccessibilityNodeInfo):Boolean{
    val rect = Rect()
    node.getBoundsInScreen(rect)
    val x = rect.left+(rect.right-rect.left)/2
    val y = rect.top+(rect.bottom-rect.top)/2
    return performClick(x.toFloat(),y.toFloat())
}


/**
 * 通过模拟手势实现点击
 */
fun AccessibilityService.performClick(x:Float,y:Float):Boolean{
    val gesture = GestureDescription.Builder().run {
        val path = Path().apply {
            moveTo(x, y)
        }
        addStroke(GestureDescription.StrokeDescription(path, 0, 1))
        build()
    }
    return dispatchGesture(gesture, object : AccessibilityService.GestureResultCallback() {
        override fun onCompleted(gestureDescription: GestureDescription?) {
            super.onCompleted(gestureDescription)
            this@performClick.log("手势完成")
        }

        override fun onCancelled(gestureDescription: GestureDescription?) {
            super.onCancelled(gestureDescription)
            this@performClick.log("手势取消")

        }
    }, null)
}