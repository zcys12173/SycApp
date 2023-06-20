package com.syc.mvvm

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.syc.mvvm.core.addCommonPlugins
import com.syc.mvvm.core.applyLocalScript
import com.syc.mvvm.core.getBuildManifestPath
import com.syc.mvvm.core.handleDependencies
import com.syc.mvvm.core.isAppModule
import com.syc.mvvm.core.tasks.MergeManifestTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            logger.lifecycle("ApplicationPlugin apply")
            configPlugin()
            handleDependencies()
            applyLocalScript()
            androidConfig()
            appendMergeManifestTask()
        }
    }

    private fun Project.configPlugin() {
        pluginManager.apply("com.android.application")
        pluginManager.apply("io.github.zcys12173.plugin_router")
        addCommonPlugins()
    }

    private fun Project.androidConfig() {
        extensions.findByType(ApplicationExtension::class.java)?.run {
            val applicationID =
                rootProject.extensions.extraProperties.get("APPLICATION_ID") as String
            defaultConfig.applicationId = applicationID
            if (!isAppModule) {
                defaultConfig.applicationIdSuffix = ".${name.replace("-", "_")}"
                val manifestPath = getBuildManifestPath(project)
                println("manifest path:$manifestPath")
                sourceSets.getByName("main").apply {
                    manifest.srcFile("src/sample/AndroidManifest.xml")
                    java.srcDirs("src/main/java", "src/sample/java")
                    res.srcDirs("src/main/res", "src/sample/res")
                    assets.srcDirs("src/main/assets", "src/sample/assets")
                }
            }
        }
    }

    private fun Project.appendMergeManifestTask() {
        if (!isAppModule) {
            extensions.findByType(AndroidComponentsExtension::class.java)?.run {
                onVariants { variant ->
                    val taskName = "merge${variant.name.capitalize()}Manifest"
                    val mergeManifestProvider =
                        tasks.register(taskName, MergeManifestTask::class.java) {
                            it.nameSpace.set(variant.namespace)
                            it.placeHolders.set(variant.manifestPlaceholders)
                        }
                    variant.artifacts.use(mergeManifestProvider)
                        .wiredWithFiles(
                            MergeManifestTask::mergedManifest,
                            MergeManifestTask::outputManifest
                        )
                        .toTransform(SingleArtifact.MERGED_MANIFEST)
                }
            }
        }
    }
}

