package com.syc.mvvm.core.config

import com.syc.mvvm.core.ProjectType
import com.syc.mvvm.core.type
import org.gradle.api.Project

fun Project.handleDependencies() {
    val addDependency = { path:String,configurationName:String->
        val subProject = rootProject.project(path)
        val projectVersion = subProject.version
        if(projectVersion == "unspecified"){
//            println("depend $path from local")
            project.dependencies.add(configurationName, subProject)
        }else{
//            println("depend $path from remote")
            project.dependencies.add(configurationName,"$group:${subProject.name}:$projectVersion")
        }
    }
    when (type) {
        ProjectType.APP -> {
            rootProject.subprojects {
                if (it.type == ProjectType.FEATURE && it.subprojects.isEmpty()) {
                    addDependency(":${it.path}","implementation")
                }
            }
        }

        ProjectType.FEATURE -> {
            addDependency(":feature:common","api")
        }

        ProjectType.COMMON -> {
            rootProject.subprojects {
                if (it.type == ProjectType.CORE && it.subprojects.isEmpty()) {
                    addDependency(":${it.path}","api")
                }
            }
        }

        ProjectType.CORE -> {
            addDependency(":core:framework","api")
        }

        ProjectType.FRAMEWORK -> {
            //do nothing
        }
    }
}