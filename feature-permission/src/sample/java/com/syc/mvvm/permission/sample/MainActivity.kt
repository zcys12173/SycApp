package com.syc.mvvm.permission.sample

import android.Manifest
import android.os.Bundle
import android.widget.Button
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.utils.showToast
import com.syc.mvvm.permission.Permission
import com.syc.mvvm.permission.R
import com.syc.mvvm.permission.withPermission

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.tv).setOnClickListener {
            withPermission(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,callback =
                object : Permission.Callback {
                    override fun onGranted(permissions: Array<String>) {
                        showToast("权限申请成功")
                    }

                    override fun onDenied(permissions: Array<String>) {
                        showToast("权限申请失败")
                    }
                })
        }
    }
}


