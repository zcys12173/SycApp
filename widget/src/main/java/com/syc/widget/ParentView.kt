package com.syc.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

class ParentView @JvmOverloads constructor(context:Context,attr: AttributeSet?=null,defStyle:Int=0):LinearLayout(context,attr,defStyle) {

    init {
        orientation = VERTICAL
    }
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return ev?.action == MotionEvent.ACTION_MOVE
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            Log.e("syc","parent action:$action")
        }
        return super.onTouchEvent(event)
    }
}