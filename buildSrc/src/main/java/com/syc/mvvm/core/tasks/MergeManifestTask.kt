//package com.syc.mvvm.core.tasks
//
//import com.android.manifmerger.ManifestMerger2
//import com.android.manifmerger.MergingReport
//import com.android.utils.FileUtils
//import com.android.utils.StdLogger
//import org.gradle.api.DefaultTask
//import org.gradle.api.tasks.TaskAction
//import java.io.File
//
//abstract class MergeManifestTask : DefaultTask() {
//
//    @TaskAction
//    fun action() {
//        val basePath = "${project.projectDir}/src/sample/AndroidManifest.xml"
//        val mergePath = "${project.projectDir}/src/main/AndroidManifest.xml"
//        val invoker = ManifestMerger2.newMerger(
//            File(basePath), StdLogger(StdLogger.Level.VERBOSE),
//            ManifestMerger2.MergeType.APPLICATION
//        )
//        invoker.addLibraryManifest("module",File(mergePath))
//        val report = invoker.merge()
//        println(report.reportString)
//        if(report.result == MergingReport.Result.SUCCESS){
//            val content = report.getMergedDocument(MergingReport.MergedManifestKind.MERGED)
//            FileUtils.writeToFile(File(basePath),content)
//        }else{
//            report.loggingRecords.forEach {
//                println(it.toString())
//            }
//        }
//        println("merge manifest finish")
//    }
//}
