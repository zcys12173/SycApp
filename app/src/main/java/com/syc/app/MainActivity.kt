package com.syc.app

import android.os.Bundle
import com.syc.app.databinding.ActivityMainBinding
import com.syc.mvvm.mvvm.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initViewModel(savedInstanceState: Bundle?) {
        val vm = TestViewModel()
        bindViewModel(vm)
        rootBinding.vm = vm
    }

    override fun init(savedInstanceState: Bundle?) {
        rootBinding.root.setOnClickListener{
            System.out.println("setOnClickListener")
        }
    }

}
