package com.syc.mvvm.core.tasks

import com.syc.mvvm.core.visitor.MyClassVisitor
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
abstract class MyTransformTask : DefaultTask() {

    @get:InputFiles
    abstract val allJars: ListProperty<RegularFile>

    @get:InputFiles
    abstract val allDirectories: ListProperty<Directory>

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun taskAction() {
        val needCreateTempOutputFile = allJars.get().contains(output.get())
        val outputFile = output.get().asFile
        val finalOutputFile = if(needCreateTempOutputFile){
            File(outputFile.parent,"${outputFile.name}_temp")
        }else{
            outputFile
        }
        val jarOutput = JarOutputStream(
            BufferedOutputStream(
                FileOutputStream(
                    finalOutputFile
                )
            )
        )
        println("Transform输出路径:${output.get().asFile.path}")
        allJars.get().forEach { file ->
            println("处理Jar文件：${file.asFile.absolutePath}")
            val jarFile = JarFile(file.asFile)
            jarFile.entries().iterator().forEach { jarEntry ->
                println("处理Jar 内部文件：${jarEntry.name}")
                if (jarEntry.name.shouldProcessClass()) {
                    jarFile.getInputStream(jarEntry).use {
                        val classReader = ClassReader(it)
                        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        val cv = MyClassVisitor(classWriter)
                        val options = ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES
                        classReader.accept(cv, options)
                        val bytes = classWriter.toByteArray()
                        jarOutput.putNextEntry(JarEntry(jarEntry.name))
                        jarOutput.write(bytes)
                        jarOutput.closeEntry()
                    }
                }

            }
            jarFile.close()
        }

        allDirectories.get().forEach { directory ->
            directory.asFile.walk().forEach { file ->
                if (file.isFile) {
                    println("开始处理文件：${file.path}")
                    val relativePath = directory.asFile.toURI().relativize(file.toURI()).path
                    jarOutput.putNextEntry(JarEntry(relativePath.replace(File.separatorChar, '/')))
                    file.inputStream().use {
                        if(file.path.endsWith(".class")){
                            val classReader = ClassReader(it)
                            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            val cv = MyClassVisitor(classWriter)
                            val options = ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES
                            classReader.accept(cv, options)
                            val bytes = classWriter.toByteArray()
                            jarOutput.write(bytes)
                        }else{
                            it.copyTo(jarOutput)
                        }
                    }
                    jarOutput.closeEntry()
                }
            }
        }
        jarOutput.flush()
        jarOutput.close()
        if(needCreateTempOutputFile){
            finalOutputFile.copyTo(outputFile,overwrite = true)
        }
    }

    private fun String.shouldProcessClass(): Boolean {
        return this.endsWith(".class")
                && !this.contains("META-INF")
    }

}