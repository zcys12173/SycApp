package com.syc.mvvm.mvvm.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.BaseObservable
import java.lang.ref.WeakReference

/**
 * Created by shiyucheng on 2019-11-29.
 */
open class BaseViewModel : BaseObservable()
    , VMLifecycle {
    /**
     * 当前生命周期状态
     */
    protected var state = State.INITIALIZED
        private set

    /**
     * activity跳转类
     */
    private var activityStarter: IActivityStarter? = null

    /**
     * 当前页面Activity对象，目前主要用于权限申请
     */
    private var activityRef: WeakReference<Activity>? = null

    protected fun getContext():Context? = activityRef?.get()

    /**
     * 设置当前生命周期State
     * {@link VMManager}
     */
    internal fun setLifecycleState(state: State){
        this.state = state
    }

    /**
     * 设置IActivityStarter {@link BaseActivity andd BaseFragment}
     */
    internal fun setActivityStarter(activityStarter: IActivityStarter) {
        this.activityStarter = activityStarter
    }

    /**
     * 设置Activity引用 {@link BaseActivity andd BaseFragment}
     */
    internal fun setActivity(activity:Activity) {
        activityRef = WeakReference(activity)
    }

    /**
     * 权限申请
     */
    protected fun requestPermission(permissions: Array<String>, requestCode: Int) {
        activityRef?.get()?.run {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        } ?: throw RuntimeException("activity is null")
    }

    /**
     * 权限检测
     */
    protected fun checkPermission(permission: String): Int {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return PERMISSION_GRANTED
        }
        activityRef?.get()?.run {
            return ActivityCompat.checkSelfPermission(this, permission)
        }
        throw RuntimeException("activity is null")
    }

    /**
     * Activity跳转
     */
    protected fun startActivity(intent: Intent) {
        activityStarter?.startActivity(intent)
    }
    /**
     * Activity跳转
     */
    protected fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle? = null) {
        activityStarter?.startActivityForResult(intent, requestCode, options)
    }

    @JvmOverloads
    protected fun setResult(resultCode:Int,data:Intent?=null){
        activityRef?.get()?.setResult(resultCode,data)
    }

    protected fun finish(){
        activityRef?.get()?.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {}

    override fun onNewIntent(intent: Intent?) {}

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {}
}