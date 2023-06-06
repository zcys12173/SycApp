package com.syc.mvvm.framework.utils

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.util.Size
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.syc.mvvm.framework.base.ActivityResultCallback
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.base.application


/**
 * 显示Toast
 */
fun showToast(text: String, @BaseTransientBottomBar.Duration duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(application, text, duration).show()
}

fun getScreenSize(): Size {
    val wm = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getSize(point)
    return Size(point.x, point.y)
}

fun BaseActivity.openAppSystemSetting(callback: ActivityResultCallback){
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:" + application.packageName)
    startActivity(intent,callback)
}