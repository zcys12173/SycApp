package com.syc.mvvm.ble.scan

import android.app.Service
import android.content.Intent
import android.os.IBinder


/**
 * Create by Stone Yu
 * Date:2023/11/7
 * Desc:Ble服务
 */
class BleService: Service() {
    private lateinit var binder: BleBinder
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        binder = BleBinder(this)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}