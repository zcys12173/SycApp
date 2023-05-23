package com.syc.mvvm

import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("LibraryPlugin apply")
            pluginManager.apply("com.android.library")
            val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
            apply(mutableMapOf("from" to buildScriptPath))
        }
    }
}

