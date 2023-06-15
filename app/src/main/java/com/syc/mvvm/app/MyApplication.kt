package com.syc.mvvm.app

import com.syc.mvvm.framework.base.FrameworkApplication
import com.syc.mvvm.net.Api

class MyApplication : FrameworkApplication() {
    override fun onCreate() {
        super.onCreate()
        Api.init()
    }
}