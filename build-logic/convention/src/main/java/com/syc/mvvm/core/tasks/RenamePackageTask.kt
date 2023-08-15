package com.syc.mvvm.core.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
private const val CUR_PACKAGE_NAME = "com.syc.mvvm"
private const val TARGET_PACKAGE_NAME = "com.haha.een"
abstract class RenamePackageTask: DefaultTask() {
    init {
        group = "utils"
    }
    @TaskAction
    fun action(){
        println("------")
        loopFile(project.rootDir)
        deleteEmptyFoldersRecursively(project.rootDir)
    }

    private fun loopFile(file: File){
        if(file.path.endsWith("/.") || file.path.contains("/build-cache/") || file.path.endsWith("/build")){
            return
        }
        if(file.isDirectory){
            file.listFiles()?.forEach {
                loopFile(it)
            }
        }else{
            modifyFile(file)
        }
    }


    private fun modifyFile(file:File){
        println("开始处理文件：${file.path}")
        val fileContent by lazy { file.readText()}
        if(file.path.contains(CUR_PACKAGE_NAME.toFilePath())){
            val content = fileContent.replace(CUR_PACKAGE_NAME, TARGET_PACKAGE_NAME)
            val path = file.path.replace(CUR_PACKAGE_NAME.toFilePath(), TARGET_PACKAGE_NAME.toFilePath())
            val newFile = createFile(path)
            newFile.writeText(content)
            file.delete()
        }else if(fileContent.contains(
                CUR_PACKAGE_NAME
            )){
            file.writeText(fileContent.replace(CUR_PACKAGE_NAME, TARGET_PACKAGE_NAME))
        }
    }

    private fun String.toFilePath():String{
        return this.replace(".","/")
    }

    private fun createFile(path:String):File{
        val file = File(path)
        if(file.exists()){
            throw FileAlreadyExistsException(file)
        }
        file.parentFile.mkdirs()
        file.createNewFile()
        return file
    }

    private fun deleteEmptyFoldersRecursively(dir: File) {
        dir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                deleteEmptyFoldersRecursively(file)
                if (file.listFiles()?.isEmpty() == true) {
                    file.delete()
                }
            }
        }
    }
}