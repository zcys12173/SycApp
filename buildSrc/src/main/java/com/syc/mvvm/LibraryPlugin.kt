package com.syc.mvvm

import com.syc.mvvm.core.addCommonPlugins
import com.syc.mvvm.core.applyLocalScript
import com.syc.mvvm.core.handleDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            addCommonPlugins()
            applyLocalScript()
            handleDependencies()
        }
    }
}

