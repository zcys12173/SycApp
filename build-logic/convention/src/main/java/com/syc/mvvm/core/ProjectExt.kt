package com.syc.mvvm.core

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

fun Project.isVersionValid():Boolean{
    return version != "unspecified" && version.toString().isNotEmpty()
}
fun Project.findLibrary(name: String): Provider<MinimalExternalModuleDependency> {
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    return libs.findLibrary(name).get()
}

fun Project.findPlugin(name: String): Provider<PluginDependency> {
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    return libs.findPlugin(name).get()
}

val Project.type: ProjectType
    get() {
        return when {
            path == ":app" -> ProjectType.APP
            path == ":feature:common" -> ProjectType.COMMON
            path == ":core:framework" -> ProjectType.FRAMEWORK
            path.startsWith(":feature") -> ProjectType.FEATURE
            path.startsWith(":test") -> ProjectType.TEST
            else -> ProjectType.CORE
        }
    }

enum class ProjectType {
    APP, FEATURE, CORE, COMMON, FRAMEWORK,TEST
}