package com.syc.aop.transform

import com.quinn.hunter.transform.HunterTransform
import org.gradle.api.Project

class AopTransform extends HunterTransform{

    AopTransform(Project project) {
        super(project)
        this.bytecodeWeaver = new AopWeaver()
    }
}