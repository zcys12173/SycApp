package com.syc.mvvm

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("ApplicationPlugin apply")
            val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
            apply(mutableMapOf("from" to buildScriptPath))
        }
    }
}

