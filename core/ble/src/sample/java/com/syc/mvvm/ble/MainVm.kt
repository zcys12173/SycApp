package com.syc.mvvm.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.syc.mvvm.ble.scan.BleBinder
import com.syc.mvvm.ble.scan.BleService
import com.syc.mvvm.framework.base.BaseViewModel
import com.syc.mvvm.framework.base.activity
import com.syc.mvvm.framework.utils.runDelay
import com.syc.mvvm.permission.Permission
import com.syc.mvvm.permission.withPermission

/**
 * Create by Stone Yu
 * Date:2023/11/9
 * Desc:
 */
class MainVm : BaseViewModel() {
    private val permissions = mutableListOf(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_ADMIN
    )
    private var bleBinder: BleBinder? = null
    private val devices = mutableListOf<BluetoothDevice>()
    val adapter = DeviceAdapter(devices)
    init {
        runDelay(3){
            checkPermission()
        }
    }

    @SuppressLint("MissingPermission")
    fun scan(){
        bleBinder?.scan(callback =
        object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                result?.run {
                    if(devices.contains(device) || device.name.isNullOrEmpty()){
                        return
                    }
                    devices.add(device)
                    adapter.notifyDataSetChanged()
                }
            }
        } )
    }
    private fun checkPermission() {
        withPermission(
            permissions = permissions.toTypedArray(),
            callback = object : Permission.Callback {
                override fun onGranted(permissions: Array<String>) {
                    bindService()
                }

                override fun onDenied(permissions: Array<String>) {
                }
            })
    }

    private fun bindService() {
        activity?.bindService(
            Intent(activity!!, BleService::class.java), connection, Context.BIND_AUTO_CREATE
        )
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            bleBinder = service as? BleBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bleBinder = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        activity?.unbindService(connection)
    }
}