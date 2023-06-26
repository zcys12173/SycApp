package com.syc.mvvm.core

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency


fun Project.findLibrary(name:String): Provider<MinimalExternalModuleDependency> {
     val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    return libs.findLibrary(name).get()
}

fun Project.findPlugin(name:String): Provider<PluginDependency> {
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    return libs.findPlugin(name).get()
}


fun Project.handleDependencies() {
    when (type) {
        ProjectType.APP -> {
            rootProject.subprojects {
                if (it.type == ProjectType.BUSINESS) {
                    this@handleDependencies.dependencies.add(
                        "implementation",
                        project(":${it.name}")
                    )
                }
            }
        }

        ProjectType.COMMON -> {
            rootProject.subprojects {
                if (it.type == ProjectType.FEATURE) {
                    this@handleDependencies.dependencies.add("api", project(":${it.name}"))
                }
            }
        }

        ProjectType.BUSINESS -> {
            project.dependencies.add("api", project(":business-common"))
        }

        ProjectType.FEATURE -> {
            project.dependencies.add("api", project(":feature-framework"))
        }

        ProjectType.FRAMEWORK -> {
            //do nothing
        }
    }
}

fun Project.addCommonPlugins() {
    pluginManager.apply("com.google.devtools.ksp")
    pluginManager.apply("org.jetbrains.kotlin.android")
}


fun Project.applyLocalScript() {
    val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
    apply(mutableMapOf("from" to buildScriptPath))
}

val Project.type: ProjectType
    get() {
        return when {
            name == "app" -> ProjectType.APP
            name.startsWith("business-") && name != "business-common" -> ProjectType.BUSINESS
            name.startsWith("feature-") && name != "feature-framework" -> ProjectType.FEATURE
            name == "business-common" -> ProjectType.COMMON
            else -> ProjectType.FRAMEWORK
        }
    }

enum class ProjectType {
    APP, BUSINESS, FEATURE, COMMON, FRAMEWORK
}