package com.syc.mvvm.app

import android.os.Bundle
import com.syc.mvvm.common.router.CHECK_LOGIN
import com.syc.mvvm.common.router.IS_LOGIN
import com.syc.mvvm.common.router.PATH_TIKTOK
import com.syc.mvvm.common.router.PATH_VERIFY
import com.syc.mvvm.common.router.SERVICE_LOGIN
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.utils.runDelay
import com.syc.router.KRouter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        GlobalScope.launch {
            val hasLogin = KRouter.loadService(SERVICE_LOGIN).callSuspend(CHECK_LOGIN) as Boolean
            if(hasLogin){
                KRouter.asNavigator(this@SplashActivity).path(PATH_TIKTOK).navigate()
            }else{
                KRouter.asNavigator(this@SplashActivity).path(PATH_VERIFY).navigate()
            }
        }
    }
}