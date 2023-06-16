package com.syc.mvvm

import com.syc.mvvm.core.addCommonPlugins
import com.syc.mvvm.core.handleDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            addCommonPlugins()
            val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
            apply(mutableMapOf("from" to buildScriptPath))
            handleDependencies()
        }
    }
}

