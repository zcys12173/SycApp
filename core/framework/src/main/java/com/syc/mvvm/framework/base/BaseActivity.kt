package com.syc.mvvm.framework.base

import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity

typealias ActivityResultCallback = () -> Unit
typealias PermissionCallback = (results: Map<String, Boolean>) -> Unit

open class BaseActivity : AppCompatActivity() {
    private val activityCallbacks = mutableListOf<ActivityResultCallback>()
    private val permissionCallbacks = mutableMapOf<Int, PermissionCallback>()

    fun startActivity(
        intent: Intent,
        callback: ActivityResultCallback
    ) {
        if (!activityCallbacks.contains(callback)) {
            activityCallbacks.add(callback)
            startActivity(intent)
        }

    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        activityCallbacks.forEach {
            it.invoke()
        }
        activityCallbacks.clear()
    }


    fun requestPermission(
        requestCode: Int,
        vararg permissions: String,
        callback: PermissionCallback
    ) {
        if (!permissionCallbacks.containsKey(requestCode)) {
            permissionCallbacks[requestCode] = callback
            requestPermissions(permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionCallbacks.containsKey(requestCode)) {
            val result = mutableMapOf<String, Boolean>()
            for (i in permissions.indices) {
                result[permissions[i]] = grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
            permissionCallbacks[requestCode]?.invoke(result)
            permissionCallbacks.remove(requestCode)
        }
    }

}
