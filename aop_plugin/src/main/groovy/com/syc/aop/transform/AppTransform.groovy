package com.syc.aop.transform

import com.android.SdkConstants
import com.android.annotations.NonNull
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.builder.utils.ZipEntryUtils
import com.google.common.io.Files
import com.syc.aop.visitor.ScanClassVisitor
import org.apache.commons.io.FileUtils
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
        return TransformManager.PROJECT_ONLY
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
        if (!isIncremental) {
            provider.deleteAll()
        }
        print('开始扫描')
        transformInvocation.inputs.forEach {
            //处理jar文件
            it.jarInputs.forEach { jarInput ->
                Status status = jarInput.status
                File inputJar = jarInput.file
                File outputJar = provider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                if (isIncremental) {
                    println("增量处理${jarInput.name}文件")
                    switch (status) {
                        case Status.NOTCHANGED:
                            break
                        case Status.CHANGED:
                        case Status.ADDED:
                            transformJar(inputJar, outputJar)
                            break;
                        case Status.REMOVED:
                            FileUtils.forceDelete(outputJar)
                            break
                    }
                } else {
                    println("非增量处理${jarInput.name}")
                    transformJar(inputJar, outputJar)
                }
            }

            //处理源文件
            it.directoryInputs.forEach {
                File inputDir = it.file
                File outputDir = provider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                if (isIncremental) {
                    it.changedFiles.entrySet().forEach { entry ->
                        File inputFile = entry.getKey()
                        Status status = entry.getValue()
                        switch (status) {
                            case Status.NOTCHANGED:
                                break
                            case Status.ADDED:
                            case Status.CHANGED:
                                if (!inputFile.isDirectory() && inputFile.name.endsWith(SdkConstants.DOT_CLASS)) {
                                    File out = toOutputFile(outputDir, inputDir, inputFile)
                                    transformFile(inputFile, out)
                                }
                                break
                            case Status.REMOVED:
                                File outputFile = toOutputFile(outputDir, inputDir, inputFile)
                                FileUtils.forceDelete(outputFile)
                                break
                        }
                    }
                } else {
                    File[] files = inputDir.listFiles()
                    for (File file : files) {
                        if (file.name.endsWith(SdkConstants.DOT_CLASS)) {
                            File out = toOutputFile(outputDir, inputDir, file)
                            transformFile(file, out)
                        }
                    }
                }
            }
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
                if (!entry.isDirectory() && entry.getName().endsWith(SdkConstants.DOT_CLASS)) {
                    println('3')
                    zos.putNextEntry(new ZipEntry(entry.getName()))
                    println('4')
                    if(inputJar.name.contains("R\$")){

                    }
                    byte[] newClass = modifyClass(zis)
                    println('5')
                    zos.write(newClass)
                } else {
                    // Do not copy resources
                }
                entry = zis.getNextEntry()
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
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile)
            byte[] newClass = modifyClass(fis)
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