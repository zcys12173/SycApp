package com.syc.mvvm.core.tasks

import com.android.manifmerger.ManifestMerger2
import com.android.manifmerger.MergingReport
import com.android.utils.StdLogger
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class MergeManifestTask : DefaultTask() {
    @get:Input
    abstract val nameSpace: Property<String>

    @get:Input
    abstract val placeHolders: MapProperty<String, String>

    @get:InputFile
    abstract val mergedManifest: RegularFileProperty

    @get:OutputFile
    abstract val outputManifest: RegularFileProperty

    @TaskAction
    fun action() {
        val mergePath = "${project.projectDir}/src/main/AndroidManifest.xml"
        val invoker = ManifestMerger2.newMerger(
            mergedManifest.get().asFile, StdLogger(StdLogger.Level.VERBOSE),
            ManifestMerger2.MergeType.APPLICATION
        )
        invoker.addLibraryManifest(project.name, File(mergePath))
        invoker.setPlaceHolderValues(placeHolders.get() as Map<String, String>)
        invoker.addAllowedNonUniqueNamespace(nameSpace.get())
        val report = invoker.merge()
        println(report.reportString)
        if (report.result.isSuccess) {
            val content = report.getMergedDocument(MergingReport.MergedManifestKind.MERGED)
            outputManifest.get().asFile.writeText(content)
            println("merge manifest success")
        } else {
            val sb = StringBuilder()
            report.loggingRecords.forEach {
                sb.append(it.toString())
            }
            throw MergeManifestException(sb.toString())
        }
    }


}

class MergeManifestException(msg: String) : RuntimeException(msg)
