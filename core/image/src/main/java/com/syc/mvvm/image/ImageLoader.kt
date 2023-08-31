package com.syc.mvvm.image

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import coil.transform.RoundedCornersTransformation
import com.syc.mvvm.framework.base.application


/**
 * 加载图片
 * @param data 支持类型：[String
    HttpUrl
    Uri (android.resource, content, file, http, and https schemes)
    File
    @DrawableRes Int
    Drawable
    Bitmap
    ByteArray
    ByteBuffer]
 */
fun ImageView.load(data: Any,placeHolder:Drawable?=null,error:Drawable?=null,radius: Radius?=null) {
    load(data, coilImageLoader){
        placeholder(placeHolder)
        error(error)
        radius?.let {
            transformations(RoundedCornersTransformation(radius.topLeft,radius.topRight,radius.bottomLeft,radius.bottomRight))
        }

    }
}



private val coilImageLoader = ImageLoader.Builder(application)
    .crossfade(true)
    .memoryCache {
        MemoryCache.Builder(application)
            .maxSizePercent(0.25)
            .build()
    }
    .diskCache {
        DiskCache.Builder()
            .directory(application.cacheDir.resolve("image_cache"))
            .maxSizePercent(0.02)
            .build()
    }
    .components {
        add(SvgDecoder.Factory())
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }
    .build()
