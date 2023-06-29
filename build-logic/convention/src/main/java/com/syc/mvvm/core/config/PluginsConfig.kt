package com.syc.mvvm.core.config

import org.gradle.api.Project


fun Project.addCommonPlugins() {
//    pluginManager.apply("com.google.devtools.ksp")
    pluginManager.apply("kotlin-kapt")
    pluginManager.apply("org.jetbrains.kotlin.android")
}
