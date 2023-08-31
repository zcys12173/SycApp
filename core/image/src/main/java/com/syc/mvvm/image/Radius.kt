package com.syc.mvvm.image

import androidx.annotation.Px

data class Radius(
    @Px val topLeft: Float = 0f,
    @Px val topRight: Float = 0f,
    @Px val bottomLeft: Float = 0f,
    @Px val bottomRight: Float = 0f
){
    companion object{
        fun all(@Px radius:Float):Radius{
            return Radius(radius,radius,radius,radius)
        }
    }
}