package com.syc.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel


class MyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.log(LogLevel.LIFECYCLE, "----------------------buildSrc Plugin-----------------")

    }
}