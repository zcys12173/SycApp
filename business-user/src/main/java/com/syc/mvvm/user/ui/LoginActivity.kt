package com.syc.mvvm.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.syc.mvvm.common.router.PATH_LOGIN
import com.syc.mvvm.user.R
import com.syc.mvvm.user.databinding.ActivityLoginBinding
import com.syc.mvvm.user.vm.LoginVm
import com.syc.router.annotations.RouterPage

@RouterPage(PATH_LOGIN)
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
        binding.vm = LoginVm()
    }
}