package com.syc.mvvm.framework.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process

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

/**
 * 设置前后台切换监听
 */
fun Application.addForegroundChangedListener(listener: (isForeground: Boolean) -> Unit) {
    registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
            if (activityCount == 0) {
                listener.invoke(true)
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
                listener.invoke(false)
            }

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

    })
}