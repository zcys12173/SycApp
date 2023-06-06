package com.syc.mvvm.accessibility.utils

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.accessibility.AccessibilityNodeInfo
import com.syc.mvvm.framework.utils.log

/**
 * 模拟点击事件
 * @param service 传入AccessibilityService，如果传入则使用模拟手势操作
 */
fun AccessibilityNodeInfo.performClick(service: AccessibilityService?=null):Boolean{
    var result = performAction(AccessibilityNodeInfo.ACTION_CLICK)
    if(result){
        log("执行点击事件成功")
    }else{
        log("执行点击事件失败：$this")
        if(service != null){
            log("使用模拟手势操作")
            result = service.performClick(this)
            if(result){
                log("模拟手势操作成功")
            }else{
                log("模拟手势操作失败")
            }
        }
    }
    return result
}


/**
 * 模拟输入事件
 * @param text 输入的文本
 */
fun AccessibilityNodeInfo.performInput(text:String):Boolean {
    val arguments = Bundle()
    arguments.putCharSequence(
        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
        text
    )
    val result = performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
    if(result){
        log("执行输入 \"$text\" 事件成功")
    }else{
        log("执行输入 \"$text\" 事件失败，$this")
    }
    return result
}

