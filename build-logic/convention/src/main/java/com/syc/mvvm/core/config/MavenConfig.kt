package com.syc.mvvm.core.config

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.internal.dependencies.DefaultMavenDependency
import org.gradle.api.publish.maven.internal.publication.DefaultMavenPublication
import java.io.File
import java.util.Properties

/**
 * Maven相关配置
 * 1.添加maven私服仓库
 * 2.配置maven私服发布配置
 *  group={MAVEN_GROUP}  artifactId={project.name}  version={project.version}
 */
fun Project.applyMaven() {
    val properties= Properties()
    val file = File(rootDir,"maven.properties")
    if(!file.exists()){
        return
    }
    properties.load(file.inputStream())
    val snapshotUrl = properties.getProperty("snapshotUrl")
    val releaseUrl = properties.getProperty("releaseUrl")
    val userName = properties.getProperty("userName")
    val password = properties.getProperty("password")

    //添加maven插件
    pluginManager.apply("maven-publish")
    //添加maven仓库方法封装
    val addMavenRepositories = { url:String, handler: RepositoryHandler ->
        handler.maven { repository ->
            with(repository) {
                setUrl(url)
                credentials { credentials ->
                    credentials.username = userName
                    credentials.password = password
                }
            }
        }
    }
    //添加仓库
//    addMavenRepositories(REPOSITORY_URL_RELEASE,repositories)
//    addMavenRepositories(REPOSITORY_URL_SNAPSHOT,repositories)
    //配置发布信息
    afterEvaluate {
        val artifactId = name
        extensions.configure(PublishingExtension::class.java) {
            it.publications.create(artifactId.capitalize(), MavenPublication::class.java) { publication ->
                publication.groupId = group as? String
                publication.artifactId = artifactId
                publication.version = project.version as? String
                publication.artifact("${project.buildDir.path}/outputs/aar/${this@applyMaven.name}-release.aar")
                if(publication is DefaultMavenPublication){
                    project.addDependenciesToMavenPublication(publication)
                }
            }
            if (version.toString().endsWith("snapshot", ignoreCase = true)){
                addMavenRepositories(snapshotUrl,it.repositories)
            }else{
                addMavenRepositories(releaseUrl,it.repositories)
            }

        }

        tasks.findByName("assembleRelease")?.run {
            tasks.forEach {
                if(it.group == "publishing" && it.name.startsWith("publish")){
                    it.dependsOn(this)
                }
            }
        }
    }
}

/**
 * 添加项目依赖到发布产物
 */
private fun Project.addDependenciesToMavenPublication(publication: DefaultMavenPublication){
    val apiList = configurations.findByName("api")?.dependencies?.filter {
        it is DefaultExternalModuleDependency && it.group != "androidx.databinding"
    }
    val implementationList = configurations.findByName("implementation")?.dependencies?.filter {
        it is DefaultExternalModuleDependency && it.group != "androidx.databinding"
    }
    apiList?.forEach {
        publication.apiDependencies.add(DefaultMavenDependency(it.group,it.name,it.version))
    }
    implementationList?.forEach {
        publication.runtimeDependencies.add(DefaultMavenDependency(it.group,it.name,it.version))
    }
}
