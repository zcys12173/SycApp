package com.syc.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class ChildView @JvmOverloads constructor(context:Context, attr: AttributeSet?=null, defStyle:Int=0):View(context,attr,defStyle) {


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            Log.e("syc","child action:$action")
        }
        return super.onTouchEvent(event)
    }
}