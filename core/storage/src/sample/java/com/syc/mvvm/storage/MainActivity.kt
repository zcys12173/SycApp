package com.syc.mvvm.storage

import android.os.Bundle
import com.syc.mvvm.framework.base.BaseActivity

class MainActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}