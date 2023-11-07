package com.syc.mvvm.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import com.syc.mvvm.ble.scan.BleBinder
import com.syc.mvvm.ble.scan.BleService
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.utils.log
import com.syc.mvvm.permission.Permission
import com.syc.mvvm.permission.withPermission

class MainActivity : BaseActivity() {
    private var bleBinder: BleBinder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        findViewById<TextView>(R.id.tv).setOnClickListener {
            startScan()
        }
    }

    private fun checkPermission() {
        withPermission(Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADMIN,
            callback = object : Permission.Callback {
                override fun onGranted(permissions: Array<String>) {
                    bindService(
                        Intent(this@MainActivity, BleService::class.java),
                        object : ServiceConnection {
                            override fun onServiceConnected(
                                name: ComponentName?,
                                service: IBinder?
                            ) {
                                bleBinder = service as? BleBinder
                            }

                            override fun onServiceDisconnected(name: ComponentName?) {
                                bleBinder = null
                            }

                        },
                        Context.BIND_AUTO_CREATE
                    )
                }


                override fun onDenied(permissions: Array<String>) {

                }

            })
    }

    private val bluetoothAdapter by lazy {
        (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }
    private val bluetoothScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    @SuppressLint("MissingPermission")
    private fun startScan() {
        bleBinder?.scan(callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                if (!result?.device?.name.isNullOrEmpty()) {
                    log("BLE", "name:${result?.device?.name},address:${result?.device?.address}")
                }
//             \\
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>?) {
                super.onBatchScanResults(results)
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                log("BLE", "ScanFailed:$errorCode")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}