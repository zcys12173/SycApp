package com.syc.mvvm.framework.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import androidx.annotation.MainThread

/**
 * 判断是否是主进程
 */
fun Application.isMainProcess(): Boolean {
    val pid = Process.myPid()
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val currentProcessName = activityManager.runningAppProcesses.find {
        it.pid == pid
    }?.processName ?: ""
    return currentProcessName == packageName
}






private var activityCount = 0
typealias AppForegroundChangedListener = (isForeground: Boolean) -> Unit
private val appForegroundChangedListeners = mutableListOf<AppForegroundChangedListener>()
private val activityLifecycleCallback = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (activityCount == 0) {
            appForegroundChangedListeners.forEach {
                it.invoke(true)
            }
        }
        activityCount++

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        activityCount--
        if (activityCount == 0) {
            appForegroundChangedListeners.forEach {
                it.invoke(false)
            }
        }

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}
private var hasRegisterCallback = false
@MainThread
fun Application.addForegroundChangedListener(listener: AppForegroundChangedListener) {
    if(appForegroundChangedListeners.contains(listener)){
        return
    }
    appForegroundChangedListeners.add(listener)
    if(!hasRegisterCallback){
        hasRegisterCallback = true
        registerActivityLifecycleCallbacks(activityLifecycleCallback)
    }
}

fun Application.removeForegroundChangedListener(listener: AppForegroundChangedListener){
    appForegroundChangedListeners.remove(listener)
    if(appForegroundChangedListeners.isEmpty()){
        unregisterActivityLifecycleCallbacks(activityLifecycleCallback)
        activityCount = 0
        hasRegisterCallback = false
    }
}