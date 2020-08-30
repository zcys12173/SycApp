package com.syc.router

import android.os.Bundle

/**
 * 路由
 */
class Router private constructor(){
    /**
     * 单例模式
     */
    companion object val instance by lazy { Router() }

    fun build(scheme:String){
        val bundle = Bundle();
    }



}