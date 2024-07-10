package com.syc.mvvm.core.track

import com.syc.mvvm.core.ASM_VERSION
import com.syc.mvvm.core.logE
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter


/**
 * create by syc, 2024/7/10
 */
class ActivityOnCreateMethodVisitor(
    private val className:String,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?
) :
    AdviceAdapter(ASM_VERSION, mv, access, name, descriptor) {
    override fun onMethodExit(opcode: Int) {
        mv.visitFieldInsn(GETSTATIC,"com/syc/mvvm/track/Tracker","INSTANCE","Lcom/syc/mvvm/track/Tracker;")
        mv.visitVarInsn(ALOAD,0)
        mv.visitMethodInsn(INVOKEVIRTUAL,"com/syc/mvvm/track/Tracker","track","(Landroid/app/Activity;)V",false)
    }
}