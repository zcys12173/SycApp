package com.syc.mvvm.track

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.syc.mvvm.framework.utils.showToast

/**
 * create by syc, 2024/7/10
 */
object Tracker {
    fun track(activity: Activity){
        loopView(activity.window.decorView)
    }

    private fun loopView(view: View){
        if(view is ViewGroup){
            for (i in 0 until view.childCount){
                loopView(view.getChildAt(i))
            }
        }else{
            if(view is Button){
                view.setOnLongClickListener {
                    showToast("on long click")
                    true
                }
            }
        }
    }
}