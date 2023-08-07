package com.syc.mvvm

import com.syc.mvvm.core.config.addCommonPlugins
import com.syc.mvvm.core.config.applyLocalScript
import com.syc.mvvm.core.config.applyMaven
import com.syc.mvvm.core.config.configKotlinAndroid
import com.syc.mvvm.core.config.handleDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            addCommonPlugins()
            applyLocalScript()
            applyMaven()
            handleDependencies()
            configKotlinAndroid()
        }
    }
}

