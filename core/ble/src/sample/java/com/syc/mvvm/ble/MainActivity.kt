package com.syc.mvvm.ble

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.syc.mvvm.ble.databinding.ActivityMainBinding
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.base.createViewModel

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val vm = createViewModel(this, MainVm::class.java)
        binding.vm = vm
        binding.rvDevice.layoutManager = LinearLayoutManager(this)
    }
}