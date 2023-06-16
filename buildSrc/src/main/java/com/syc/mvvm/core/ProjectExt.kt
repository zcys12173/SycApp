package com.syc.mvvm.core

import org.gradle.api.Project

val Project.isAppModule
    get() = name == "app"

val Project.isBusinessModule
    get() = name.startsWith("business-") && name != "business-common"

val Project.isFeatureModule
    get() = name.startsWith("feature-") && name != "feature-framework"

val Project.isCommonModule
    get() = name == "business-common"

fun Project.handleDependencies(){
    if(isAppModule){
        rootProject.subprojects {
            if(it.isBusinessModule){
                this@handleDependencies.dependencies.add("implementation",project(":${it.name}"))
            }
        }
    }else if(isBusinessModule){
        project.dependencies.add("api",project(":business-common"))
    }else if(isFeatureModule){
        project.dependencies.add("api",project(":feature-framework"))
    }else if(isCommonModule){
        rootProject.subprojects {
            if(it.isFeatureModule){
                this@handleDependencies.dependencies.add("api",project(":${it.name}"))
            }
        }
    }

}

fun Project.addCommonPlugins(){
    pluginManager.apply("kotlin-kapt")
}