package com.syc.app

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

class CustomEditText(context: Context, attrs: AttributeSet? = null):
    AppCompatEditText(context,attrs) {
    constructor(context: Context):this(context,null)
}


@BindingAdapter("onTextChanged")
fun onTextChanged(view:CustomEditText,watcher:TextWatcher?){
    view.addTextChangedListener(watcher)
}