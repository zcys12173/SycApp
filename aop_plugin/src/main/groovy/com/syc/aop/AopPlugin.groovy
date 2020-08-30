package com.syc.aop

import com.android.build.gradle.AppExtension
import com.syc.aop.transform.AopTransform
import com.syc.aop.transform.AppTransform
import org.gradle.api.Plugin
import org.gradle.api.Project
//:\android_workplace\SycApp\app\build\intermediates\transforms\AopTransform\debug
class AopPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //判断是否是android工程
        print("开始执行aop_plugin")
//        if(!project.plugins.contains('com.android.application')){
//            print("结束执行，不是一个android工程")
//            return
//        }

        //注册transform
        print("注册transform")
        AppExtension android = project.extensions.getByType(AppExtension)
        android.registerTransform(new AopTransform(project))
//        android.registerTransform(new AppTransform())




    }
}