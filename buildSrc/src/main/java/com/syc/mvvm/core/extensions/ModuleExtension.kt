package com.syc.mvvm.core.extensions

import org.gradle.api.provider.Property

interface ModuleExtension {
    val moduleType:Property<String>
}