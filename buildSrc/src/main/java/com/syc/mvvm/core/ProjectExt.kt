package com.syc.mvvm.core

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project


fun Project.configAndroid(block: ApplicationExtension.() -> Unit){
    val appExt = extensions.findByType(ApplicationExtension::class.java)
    if(appExt != null){
        block(appExt)
    }else{
        this.extensions.configure(ApplicationExtension::class.java, block)
    }

}