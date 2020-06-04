package com.syc.framework.mvvm.viewmodel

import android.content.Intent
import android.os.Bundle

/**
 * Created by shiyucheng on 2019-11-29.
 */
interface VMLifecycle {
    
    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    /**
     * 只有在Activity中才会调用
     */
    fun onNewIntent(intent: Intent?)

    fun onSaveInstanceState(outState: Bundle)

    /**
     * 只有在Activity中才会调用
     */
    fun onRestoreInstanceState(savedInstanceState: Bundle?)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}

enum class State {
    INITIALIZED,

    CREATED,

    RESUMED,

    STARTED,

    PAUSED,

    STOPPED,

    DESTROYED,
}