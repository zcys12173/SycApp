package com.syc.mvvm.common.databinding

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter

@BindingAdapter("textChangedListener")
fun bindTextChanged(view:EditText,listener:((text:String)->Unit)?){
    view.addTextChangedListener {
        listener?.invoke(it?.toString()?:"")
    }
}