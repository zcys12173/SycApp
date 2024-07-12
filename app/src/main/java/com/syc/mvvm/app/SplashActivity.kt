package com.syc.mvvm.app

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.syc.mvvm.common.router.CHECK_LOGIN
import com.syc.mvvm.common.router.PATH_TIKTOK
import com.syc.mvvm.common.router.PATH_VERIFY
import com.syc.mvvm.common.router.SERVICE_LOGIN
import com.syc.mvvm.framework.base.BaseActivity
import com.syc.mvvm.track.Tracker
import com.syc.router.KRouter
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            val hasLogin = KRouter.loadService(SERVICE_LOGIN).callSuspend(CHECK_LOGIN) as Boolean
            if(hasLogin){
                KRouter.asNavigator(this@SplashActivity).path(PATH_TIKTOK).navigate()
            }else{
                KRouter.asNavigator(this@SplashActivity).path(PATH_VERIFY).navigate()
            }
            finish()
        }
        if(Tracker.isTrackerSetting){
            return
        }
    }
}