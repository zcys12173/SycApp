package com.syc.mvvm

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.syc.mvvm.core.extensions.ModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            logger.lifecycle("ApplicationPlugin apply")
            val buildScriptPath = "${rootDir}/build-script/android_module_build.gradle"
            apply(mutableMapOf("from" to buildScriptPath))

            extensions.findByType(ApplicationExtension::class.java)?.run {
                println("versionName: ${defaultConfig.versionName}")
                val applicationID =  target.rootProject.extensions.extraProperties.get("APPLICATION_ID") as String
                println("set applicationID: $applicationID")
                defaultConfig.applicationId = applicationID
                if(target.name != "app"){
                    defaultConfig.applicationIdSuffix = ".${target.name.replace("-","_")}"
                    sourceSets.getByName("main").apply {
                        manifest.srcFile("src/sample/AndroidManifest.xml")
                        java.srcDirs("src/main/java","src/sample/java")
                        res.srcDirs("src/main/res","src/sample/res")
                        assets.srcDirs("src/main/assets","src/sample/assets")
                    }
                }
            }
        }
    }
}

