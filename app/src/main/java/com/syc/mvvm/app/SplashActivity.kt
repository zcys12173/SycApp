package com.syc.mvvm.app

import android.os.Bundle
import com.syc.mvvm.common.router.PATH_TIKTOK
import com.syc.mvvm.common.router.PATH_VERIFY
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.framework.utils.runDelay
import com.syc.router.KRouter

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        runDelay(500) {
            KRouter.asNavigator(this@SplashActivity).path(PATH_VERIFY).navigate()
//            startActivity(Intent(this@SplashActivity,ScrollerActivity::class.java))
        }
    }
}