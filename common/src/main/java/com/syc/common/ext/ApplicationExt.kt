package com.syc.common.ext

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process


/**
 * 判断当前Application是否为主进程
 */
fun Application.isMainProcess() = packageName == getCurrentProcessName(this)

/**
 * 获取当前进程名
 */
private fun getCurrentProcessName(application: Application): String? {
    val manager = application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.runningAppProcesses.find { it.pid == Process.myPid() }?.processName?:""

}