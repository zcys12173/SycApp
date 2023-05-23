package com.syc.mvvm

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("ApplicationPlugin apply")
            pluginManager.apply("com.android.application")

            val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
            apply(mutableMapOf("from" to buildScriptPath))

            extensions.findByType(ApplicationExtension::class.java)?.run {
                val applicationID =
                    target.rootProject.extensions.extraProperties.get("APPLICATION_ID") as String
                defaultConfig.applicationId = applicationID
                if (target.name != "app") {
                    defaultConfig.applicationIdSuffix = ".${target.name.replace("-", "_")}"
                    sourceSets.getByName("main").apply {
                        manifest.srcFile("src/sample/AndroidManifest.xml")
                        java.srcDirs("src/main/java", "src/sample/java")
                        res.srcDirs("src/main/res", "src/sample/res")
                        assets.srcDirs("src/main/assets", "src/sample/assets")
                    }
                }
            }
        }
    }
}

