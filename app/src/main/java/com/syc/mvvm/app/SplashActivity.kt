package com.syc.mvvm.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syc.mvvm.common.router.PATH_LOGIN
import com.syc.mvvm.framework.utils.runDelay
import com.syc.router.KRouter

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        runDelay(500) {
            KRouter.asNavigator(this@SplashActivity).path(PATH_LOGIN).navigate()
        }
    }
}