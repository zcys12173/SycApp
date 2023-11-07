package com.syc.mvvm.ble.scan

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Context
import androidx.annotation.RequiresPermission

/**
 * Create by Stone Yu
 * Date:2023/11/7
 * Desc:
 */
open class BleConnection(protected open val device:BluetoothDevice):BluetoothGattCallback() {
    private var gatt:BluetoothGatt?=null
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun connect(context:Context,autoConnect:Boolean = true){
        gatt = device.connectGatt(context,autoConnect,this)
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        if(newState == BluetoothProfile.STATE_CONNECTED){

        }else if(status == BluetoothGatt.GATT_FAILURE){

        }
    }


}