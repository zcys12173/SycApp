package com.syc.mvvm.mvvm.viewmodel

import android.content.Intent
import android.os.Bundle

/**
 * Created by shiyucheng on 2019-11-30.
 * Activity 启动类
 */
interface IActivityStarter {
    fun startActivity(intent: Intent)

    fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?)
}