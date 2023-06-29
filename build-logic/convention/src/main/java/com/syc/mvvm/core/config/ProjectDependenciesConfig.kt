package com.syc.mvvm.core.config

import com.syc.mvvm.core.ProjectType
import com.syc.mvvm.core.type
import org.gradle.api.Project

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
            project.dependencies.add("api", project(":feature:common"))
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