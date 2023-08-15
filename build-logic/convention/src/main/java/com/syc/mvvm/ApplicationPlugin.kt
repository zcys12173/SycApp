package com.syc.mvvm

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import com.syc.mvvm.core.APPLICATION_ID
import com.syc.mvvm.core.ProjectType
import com.syc.mvvm.core.config.addCommonPlugins
import com.syc.mvvm.core.config.applyLocalScript
import com.syc.mvvm.core.config.configKotlinAndroid
import com.syc.mvvm.core.config.handleDependencies
import com.syc.mvvm.core.tasks.MergeManifestTask
import com.syc.mvvm.core.tasks.MyTransformTask
import com.syc.mvvm.core.tasks.RenamePackageTask
import com.syc.mvvm.core.type
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configPlugin()
            handleDependencies()
            applyLocalScript()
            androidConfig()
            appendMergeManifestTask()
            configKotlinAndroid()
            tasks.register("RenamePackageTask",RenamePackageTask::class.java){

            }
        }
    }



    private fun Project.configPlugin() {
        pluginManager.apply("com.android.application")
        registerTransformTask()
        pluginManager.apply("io.github.zcys12173.plugin_router")
        addCommonPlugins()
    }

    private fun Project.androidConfig() {
        extensions.configure(ApplicationExtension::class.java) { appExtension ->
            appExtension.defaultConfig.applicationId = APPLICATION_ID
            if (type != ProjectType.APP) {
                appExtension.defaultConfig.applicationIdSuffix = ".${name.replace("-", "_")}"
                appExtension.sourceSets.getByName("main").apply {
                    manifest.srcFile("src/sample/AndroidManifest.xml")
                    java.srcDirs("src/main/java", "src/sample/java")
                    res.srcDirs("src/main/res", "src/sample/res")
                    assets.srcDirs("src/main/assets", "src/sample/assets")
                }
            }
        }
    }

    private fun Project.appendMergeManifestTask() {
        if (type != ProjectType.APP) {
            extensions.configure(AndroidComponentsExtension::class.java) { androidExtension ->
                androidExtension.onVariants { variant ->
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

    private fun Project.registerTransformTask() {
        extensions.configure(AndroidComponentsExtension::class.java) { androidExtension ->
            androidExtension.onVariants { variant ->
                val taskName = "MyTransform${variant.buildType?.capitalize()}${variant.flavorName?.capitalize()}Task"
                val taskProvider = tasks.register(
                    taskName,
                    MyTransformTask::class.java
                )
                variant.artifacts.forScope(ScopedArtifacts.Scope.ALL)
                    .use(taskProvider)
                    .toTransform(
                        ScopedArtifact.CLASSES,
                        MyTransformTask::allJars,
                        MyTransformTask::allDirectories,
                        MyTransformTask::output
                    )
            }
        }
    }

}

