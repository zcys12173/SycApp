package com.syc.mvvm.core

import org.gradle.api.Project

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