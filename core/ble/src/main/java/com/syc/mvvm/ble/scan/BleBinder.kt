package com.syc.mvvm.ble.scan

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Binder
import androidx.annotation.RequiresPermission

/**
 * Create by Stone Yu
 * Date:2023/11/7
 * Desc:BleBinder，提供Ble具体功能
 */
class BleBinder(private val context:Context):Binder() {
    private val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val adapter = manager.adapter
    private val scanner = adapter.bluetoothLeScanner
    private val defaultScanSetting by lazy {
        ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
    }
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun scan(filters:List<ScanFilter> = mutableListOf(),setting: ScanSettings = defaultScanSetting, callback:ScanCallback){
        scanner.startScan(filters,setting, callback)
    }
}