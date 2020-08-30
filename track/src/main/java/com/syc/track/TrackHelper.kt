@file:JvmName("TrackHelper")
package com.syc.track

import android.util.Log
import android.view.View

fun track(view: View){
    Log.e("TrackHelper",view.tag.toString())
}