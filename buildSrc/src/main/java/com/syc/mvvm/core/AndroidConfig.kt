package com.syc.mvvm.core

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

fun ApplicationExtension.configureKotlinAndroid(){
    compileSdk = COMPILE_SDK
    with(defaultConfig){
        targetSdk = TARGET_SDK
        minSdk = MIN_SDK
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8.toString()
//    }
}

//fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
//    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
//}


fun Project.applyPlugins(){
    pluginManager.apply("org.jetbrains.kotlin.android")
}