package com.syc.mvvm

import com.syc.mvvm.core.handleDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("LibraryPlugin apply")
            pluginManager.apply("com.android.library")
            handleDependencies()
            val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
            apply(mutableMapOf("from" to buildScriptPath))
        }
    }
}

