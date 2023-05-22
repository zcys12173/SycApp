package com.syc.mvvm.user

import com.syc.mvvm.common.router.GET_USER_NAME
import com.syc.mvvm.common.router.SERVICE_LOGIN
import com.syc.router.annotations.RouterService
import com.syc.router.annotations.ServiceMethod


@RouterService(SERVICE_LOGIN)
class LoginService {

    @ServiceMethod(GET_USER_NAME)
    fun getUserName(): String {
        return "syc"
    }
}