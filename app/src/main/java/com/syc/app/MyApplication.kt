package com.syc.app

import android.app.Application
import android.content.Context
import com.syc.common.ext.isMainProcess
import com.syc.net.HttpConfig
import com.syc.net.HttpManager

class MyApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        takeIf { isMainProcess() }?.run {
            HttpManager.instance.init(this, HttpConfig(baseUrl = BuildConfig.URL))
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        level.takeIf { it == TRIM_MEMORY_COMPLETE }?.run {
            //应用结束
        }
    }
}