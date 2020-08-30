package com.syc.aop.transform


import com.android.annotations.NonNull
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.builder.utils.ZipEntryUtils
import com.google.common.io.Files
import com.syc.aop.visitor.ScanClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class AppTransform extends Transform {
    private static final String TRANSFORM_NAME = "aop_transform"
    private boolean isIncremental = false

    @Override
    String getName() {
        return TRANSFORM_NAME
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return isIncremental
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        TransformOutputProvider provider = transformInvocation.outputProvider
        assert provider != null
        boolean isIncremental = transformInvocation.isIncremental()
//        if (!isIncremental) {
//            provider.deleteAll()
//        }
        print('开始扫描')
        transformInvocation.inputs.forEach {
            //处理jar文件
            it.jarInputs.forEach { jarInput ->
                FileInputStream fis = new FileInputStream(jarInput.file)
                ZipInputStream zis = new ZipInputStream(fis)
                ZipEntry entry = zis.getNextEntry()
                while (entry != null && ZipEntryUtils.isValidZipEntryName(entry)) {
                    if (!entry.getName().contains("R\$")) {
                        println("--------${entry.getName()}")
                    }
                    entry = zis.getNextEntry()
                }
            }
            //处理源文件
            it.directoryInputs.forEach { dirInput ->

                File inputDir = dirInput.file

                File outputDir = provider.getContentLocation(dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
                if (inputDir.isDirectory()) {
                    inputDir.listFiles().each { iFile ->
                        loopFile(iFile)
                    }
                }
            }
            println("foreach over")
        }
        println("foreach over1")
    }

    private void loopFile(File file) {
        try {
            if (file.isDirectory()) {
                file.listFiles().each {
                    loopFile(it)
                }
            } else {
                if (!file.name.contains("R\$")) {
                    println("--------${file.name}")
                }
            }
        } catch (Exception e) {
            println(e.message)
        }
    }

    @NonNull
    private static File toOutputFile(File outputDir, File inputDir, File inputFile) {
        return new File(outputDir, com.android.utils.FileUtils.relativePossiblyNonExistingPath(inputFile, inputDir));
    }


    private void transformJar(File inputJar, File outputJar) {
        Files.createParentDirs(outputJar)
        try {
            println('1')
            FileInputStream fis = new FileInputStream(inputJar)
            ZipInputStream zis = new ZipInputStream(fis)
            FileOutputStream fos = new FileOutputStream(outputJar)
            ZipOutputStream zos = new ZipOutputStream(fos)
            println('2')
            ZipEntry entry = zis.getNextEntry()
            while (entry != null && ZipEntryUtils.isValidZipEntryName(entry)) {
                if (!entry.getName().contains("R\$")) {
                    println("--------${entry.getName()}")
                }

//                if (!entry.isDirectory() && entry.getName().endsWith(SdkConstants.DOT_CLASS)) {
//                    println('3')
//                    zos.putNextEntry(new ZipEntry(entry.getName()))
//                    println('4')
//                    if(inputJar.name.contains("R\$")){
//
//                    }
//                    byte[] newClass = modifyClass(zis)
//                    println('5')
//                    zos.write(newClass)
//                } else {
//                    // Do not copy resources
//                }
//                entry = zis.getNextEntry()
            }
            println('6')
            zos.flush()
        } finally {
            println('7')
            zos.close()
            fis.close()
            zis.close()
            fos.close()
        }
    }

    private void transformFile(File inputFile, File outputFile) {
        Files.createParentDirs(outputFile);
        if (!entry.getName().contains("R\$")) {
            println("--------${File.name}")
        }
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile)
//            byte[] newClass = modifyClass(fis)
            fos.write(newClass)
            fos.flush()
        } finally {
            fos.close()
            fis.close()
        }
    }

    /**
     * 真正修改类中方法字节码
     */
    private byte[] modifyClass(InputStream inputStream) {
        println('开始修改字节码')
        ClassReader classReader = new ClassReader(inputStream)
        println('开始修改字节码1')
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
        println('开始修改字节码2')
        ScanClassVisitor classWriterWrapper = new ScanClassVisitor(classWriter)
        println('开始修改字节码3')
        try {
            classReader.accept(classWriterWrapper, 0)
        } catch (Exception e) {
            println("err:${e.message}")
            throw e
        }
        println('开始修改字节码4')
        return classWriter.toByteArray()
    }
}