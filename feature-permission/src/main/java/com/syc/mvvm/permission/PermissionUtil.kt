package com.syc.mvvm.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.base.BaseViewModel
import com.syc.mvvm.framework.base.getActivity

/**
 * 检测悬浮窗权限
 */
fun Context.hasDrawOverlaysPermission() = Settings.canDrawOverlays(this)


/**
 * 请求悬浮窗权限
 */
fun Context.requestDrawOverlaysPermission(){
    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${packageName}")).run {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(this)
    }
}

fun BaseActivity.withPermission(vararg permissions: String, callback:Permission.Callback,requestCode:Int = DEFAULT_PERMISSION_REQUEST_CODE){
    Permission.requestPermission(this,requestCode, permissions.toMutableList(),callback)
}

/**
 * 检测是否有所有的权限
 * @param permissions 权限数组
 * @return 返回没有的权限
 */
fun Context.checkPermissions(vararg permissions:String):Array<String>{
    return permissions.filter {
        !checkPermission(it)
    }.toTypedArray()
}

/**
 * 检测是否有权限
 */
fun Context.checkPermission(permission:String):Boolean{
    return ContextCompat.checkSelfPermission(this,permission) ==
            PackageManager.PERMISSION_GRANTED
}

/**
 * BaseViewModel扩展函数,支持权限申请
 */
fun BaseViewModel.withPermission(vararg permissions: String, callback:Permission.Callback){
    getActivity<BaseActivity>()?.withPermission(*permissions, callback = callback)
}
