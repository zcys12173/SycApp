package com.syc.mvvm.track

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.syc.mvvm.framework.utils.showToast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * create by syc, 2024/7/10
 */
object Tracker {
    var isTrackerSetting = false
    fun track(activity: Activity) {
        if (activity is LifecycleOwner) {
            activity.lifecycle.addObserver(ResumeObserver(activity))
        }
    }

    fun onHookedClick(view: View) {
        showToast("Hooked Click")
    }
}

private class ResumeObserver(private val activity: Activity) : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        activity.window.decorView.postDelayed({
            loopView(activity.window.decorView)
        }, 1000)
        Log.e("sss", "${activity.javaClass.simpleName}->OnResume")
    }

    private fun loopView(view: View, unique: String = "id") {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                loopView(view.getChildAt(i), "$unique _$i")
            }
        } else {
            view.setTag(R.id.view_position_tag, unique)
            if (view.hasOnClickListeners() && Tracker.isTrackerSetting) {
                view.setOnLongClickListener {
                    TrackDialog(view.context) {
                        showToast(view.getTag(R.id.view_position_tag).toString())
//                        writeToLocal(view, it)
                    }.show()
                    true
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun writeToLocal(view: View, text: String) {
        val file = File(view.context.cacheDir, "tracker.txt")
        GlobalScope.launch(Dispatchers.IO) {
            file.appendText(text)
        }
    }
}
