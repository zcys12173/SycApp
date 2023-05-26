package com.syc.mvvm.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * 检测悬浮窗权限
 */
fun Context.hasDrawOverlaysPermission() = Settings.canDrawOverlays(this)


/**
 * 请求悬浮窗权限
 */
fun Context.requestDrawOverlaysPermission(){
    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${packageName}")).run {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(this)
    }
}