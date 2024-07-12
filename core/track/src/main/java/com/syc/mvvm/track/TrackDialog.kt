package com.syc.mvvm.track

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

/**
 * create by syc, 2024/7/12
 */
class TrackDialog(context: Context,private val callback:(String)->Unit):Dialog(context) {
    private val btn by lazy {
        findViewById<Button>(R.id.btn)
    }


    private val et by lazy {
        findViewById<EditText>(R.id.et_content)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_tracker)
        btn.setOnClickListener {
            dismiss()
            callback.invoke(et.text.toString())
        }
    }
}