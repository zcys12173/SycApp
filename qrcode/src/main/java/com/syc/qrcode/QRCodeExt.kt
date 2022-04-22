package com.syc.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

private val defaultHints = mutableMapOf<EncodeHintType,Any>(
    Pair(EncodeHintType.CHARACTER_SET,"UTF-8"),
    Pair(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H),
    Pair(EncodeHintType.MARGIN,0)
)

private fun createQRCodeBitmap(content:String,width:Int,height:Int,hints:MutableMap<EncodeHintType,Any> = defaultHints):Bitmap{
    val bitMatrix = QRCodeWriter().encode(content,BarcodeFormat.QR_CODE,width,height,hints)
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
            if (bitMatrix[x, y]) {
                pixels[y * width + x] = Color.RED//黑色色块像素设置，可以通过传入不同的颜色实现彩色二维码，例如Color.argb(1,55,206,141)等设置不同的颜色。
            } else {
                pixels[y * width + x] =  Color.BLUE // 白色色块像素设置
            }
        }
    }
    return Bitmap.createBitmap(pixels,width,height,Bitmap.Config.RGB_565)
}

fun String.toQRCodeBitmap(width:Int,height:Int) = createQRCodeBitmap(this,width,height)
