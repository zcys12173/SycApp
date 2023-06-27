package com.syc.mvvm.core

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency


fun Project.findLibrary(name: String): Provider<MinimalExternalModuleDependency> {
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    return libs.findLibrary(name).get()
}

fun Project.findPlugin(name: String): Provider<PluginDependency> {
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    return libs.findPlugin(name).get()
}


fun Project.handleDependencies() {
    when (type) {
        ProjectType.APP -> {
            rootProject.subprojects {
                if (it.type == ProjectType.FEATURE && it.subprojects.isEmpty()) {
                    this@handleDependencies.dependencies.add(
                        "implementation",
                        project(":${it.path}")
                    )
                }
            }
        }

        ProjectType.FEATURE -> {
            project.dependencies.add("api", project(":core:common"))
        }

        ProjectType.COMMON -> {
            rootProject.subprojects {
                if (it.type == ProjectType.CORE && it.subprojects.isEmpty()) {
                    this@handleDependencies.dependencies.add("api", project(":${it.path}"))
                }
            }
        }

        ProjectType.CORE -> {
            project.dependencies.add("api", project(":core:framework"))
        }

        ProjectType.FRAMEWORK -> {
            //do nothing
        }
    }
}

fun Project.addCommonPlugins() {
//    pluginManager.apply("com.google.devtools.ksp")
    pluginManager.apply("kotlin-kapt")
    pluginManager.apply("org.jetbrains.kotlin.android")
}


fun Project.applyLocalScript() {
    val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
    apply(mutableMapOf("from" to buildScriptPath))
}

val Project.type: ProjectType
    get() {
        return when {
            path == ":app" -> ProjectType.APP
            path.startsWith(":feature") -> ProjectType.FEATURE
            path == ":core:common" -> ProjectType.COMMON
            path == ":core:framework" -> ProjectType.FRAMEWORK
            else -> ProjectType.CORE
        }
    }

enum class ProjectType {
    APP, FEATURE, CORE, COMMON, FRAMEWORK
}