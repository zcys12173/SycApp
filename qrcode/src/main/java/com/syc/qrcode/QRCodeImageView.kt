package com.syc.qrcode

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

class QRCodeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    fun setText(content:String){
        val bitmapWidth = width - paddingStart - paddingEnd
        val bitmapHeight = height - paddingTop - paddingBottom
        val bitmap = content.toQRCodeBitmap(bitmapWidth, bitmapHeight)
        setImageBitmap(bitmap)
    }
}

@BindingAdapter("android:text")
fun bindText(view:QRCodeImageView,text:String){
    view.setText(text)
}