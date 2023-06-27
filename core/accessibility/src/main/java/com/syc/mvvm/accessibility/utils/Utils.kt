package com.syc.mvvm.accessibility.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings

/**
 * 检测无障碍功能是否开启
 */
var isAccessibilityEnabled: Boolean = false
/**
 * 跳转到无障碍设置页面
 */
fun Context.startAccessibilitySettings(){
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}