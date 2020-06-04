package com.syc.app

import android.app.Activity
import android.os.Bundle
import com.syc.app.databinding.ActivityMainBinding
import com.syc.app.databinding.FragmentTestBinding
import com.syc.framework.mvvm.BaseActivity
import com.syc.framework.mvvm.BaseFragment

class MainFragmentActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutResId(): Int = R.layout.activity_main_fragment

    override fun initViewModel(savedInstanceState: Bundle?) {
    }

    override fun init(savedInstanceState: Bundle?) {
        val fragment = TestFragment()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
    }

}

class TestFragment : BaseFragment<FragmentTestBinding>() {
    override fun getLayoutResId() = R.layout.fragment_test

    override fun initViewModel(savedInstanceState: Bundle?) {
        val vm = TestViewModel()
        bindViewModel(vm)
        rootBinding.vm = vm

    }

    override fun init(savedInstanceState: Bundle?) {

    }

}