package com.syc.mvvm.core.config

import org.gradle.api.Project

fun Project.applyLocalScript() {
    val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
    apply(mutableMapOf("from" to buildScriptPath))
}