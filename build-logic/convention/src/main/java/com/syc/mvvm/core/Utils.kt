package com.syc.mvvm.core

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.INVOKESTATIC

/**
 * create by syc, 2024/7/10
 */
fun MethodVisitor.logE(tag:String,content:String){
    visitLdcInsn(tag)
    visitLdcInsn(content)
    visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false)

}