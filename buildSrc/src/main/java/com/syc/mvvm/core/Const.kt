package com.syc.mvvm.core

import org.gradle.api.Project

private const val MERGED_MANIFEST_PATH = "generated/manifest/AndroidManifest.xml"

fun getBuildManifestPath(project: Project) = "${project.buildDir.path}/$MERGED_MANIFEST_PATH"