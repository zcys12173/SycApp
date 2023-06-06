package com.syc.mvvm.permission

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.base.application

const val DEFAULT_PERMISSION_REQUEST_CODE = 0x100

object Permission {

    private val interceptors: MutableList<Interceptor> = mutableListOf()


    fun addInterceptor(interceptor: Interceptor) {
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor)
        }
    }

    fun removeInterceptor(interceptor: Interceptor) {
        interceptors.remove(interceptor)
    }


    fun requestPermission(
        activity: BaseActivity,
        requestCode: Int,
        permissions: MutableList<String>,
        result: Callback
    ) {
        Request(activity, requestCode, permissions, result, interceptors).request()
    }


    class Request(
        val activity: BaseActivity,
        val requestCode: Int,
        var permissions: MutableList<String>,
        val result: Callback,
        interceptors: MutableList<Interceptor>
    ) {

        private val chain: Interceptor.Chain

        init {
//            interceptors.add(RationaleInterceptor())
            interceptors.add(RealRequestInterceptor())
            chain = Interceptor.Chain(interceptors)
        }

        fun request() {
            chain.process(this)
        }

    }

    private class RealRequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain) {
            with(chain.request) {
                val permissionArray = permissions.toTypedArray()
                val denies = activity.checkPermissions(*permissionArray)
                if (denies.isEmpty()) {
                    result.onGranted(permissionArray)
                } else {
                    activity.requestPermission(requestCode, *permissionArray) { resultMap ->
                        val list = mutableListOf<String>()
                        resultMap.entries.forEach {
                            if (!it.value) {
                                list.add(it.key)
                            }
                        }
                        if (list.isEmpty()) {
                            result.onGranted(permissionArray)
                        } else {
                            result.onDenied(list.toTypedArray())
                        }
                    }

                }
            }
        }

    }

    /**
     * 处理已经永久拒绝的权限
     */
    private class RationaleInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain) {
            with(chain.request) {
                //判断是否有永久拒绝的权限
                val hasAlwaysDenied = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    permissions.any {
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            it
                        )
                    }
                } else {
                    false
                }
                if (hasAlwaysDenied) {
                    showDialog(activity, {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:" + application.packageName)
                        activity.startActivity(intent) {
                            if (permissions.any {
                                    ActivityCompat.shouldShowRequestPermissionRationale(
                                        activity,
                                        it
                                    )
                                }) {
                                result.onDenied(permissions.toTypedArray())
                            } else {
                                chain.process(chain.request)
                            }
                        }

                    }, {
                        chain.process(chain.request)
                    })
                } else {
                    chain.process(chain.request)
                }
            }
        }

        private fun showDialog(
            activity: AppCompatActivity,
            onPositive: () -> Unit,
            onNegative: () -> Unit
        ) {
            AlertDialog.Builder(activity)
                .setMessage("权限已被禁用，请手动授权")
                .setPositiveButton("去设置") { dialog, _ ->
                    dialog.dismiss()
                    onPositive.invoke()
                }
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                    onNegative.invoke()
                }
                .create().show()
        }

    }

    interface Callback {
        fun onGranted(permissions: Array<String>)
        fun onDenied(permissions: Array<String>)
    }

}