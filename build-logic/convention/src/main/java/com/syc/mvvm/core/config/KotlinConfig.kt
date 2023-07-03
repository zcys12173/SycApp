package com.syc.mvvm.core.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configKotlinAndroid(){
    extensions.configure(CommonExtension::class.java){
        it.compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
    tasks.withType(KotlinCompile::class.java){
        it.kotlinOptions {
            jvmTarget = "1.8"
//            languageVersion = "2.0"
        }
    }
//    tasks.withType(KaptWithoutKotlincTask::class.java){
//
//    }
}