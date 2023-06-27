package com.syc.mvvm.framework.base

import android.app.Application
import com.syc.mvvm.framework.utils.isMainProcess
import com.syc.router.KRouter


lateinit var application: Application

open class FrameworkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (isMainProcess()) {
            application = this
            KRouter.install()
        }
    }
}