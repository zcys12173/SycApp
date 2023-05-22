package com.syc.mvvm.user.vm

import androidx.lifecycle.ViewModel
import com.syc.mvvm.common.router.GET_USER_NAME
import com.syc.mvvm.common.router.SERVICE_LOGIN
import com.syc.mvvm.framework.utils.showToast
import com.syc.router.KRouter

class LoginVm : ViewModel() {
    fun getUserName() {
        val name = KRouter.loadService(SERVICE_LOGIN).call(GET_USER_NAME) as String
        showToast(name)
    }
}