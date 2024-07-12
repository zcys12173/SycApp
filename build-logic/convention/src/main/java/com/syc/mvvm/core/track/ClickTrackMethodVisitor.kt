package com.syc.mvvm.core.track

import com.syc.mvvm.core.ASM_VERSION
import com.syc.mvvm.core.logE

import org.objectweb.asm.Label

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter


/**
 * create by syc, 2024/7/10
 */
class ClickTrackMethodVisitor(mv: MethodVisitor, access: Int, name: String?, descriptor: String?) :
    AdviceAdapter(ASM_VERSION, mv, access, name, descriptor) {
    override fun onMethodEnter() {

//        GETSTATIC com/syc/mvvm/track/Tracker.INSTANCE : Lcom/syc/mvvm/track/Tracker;
//    INVOKEVIRTUAL com/syc/mvvm/track/Tracker.isTrackerSetting ()Z
        val elseFalseLabel = Label()
        mv.visitFieldInsn(
            GETSTATIC,
            "com/syc/mvvm/track/Tracker",
            "INSTANCE",
            "Lcom/syc/mvvm/track/Tracker;"
        )
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "com/syc/mvvm/track/Tracker",
            "isTrackerSetting",
            "()Z",
            false
        )
        mv.visitJumpInsn(Opcodes.IFEQ, elseFalseLabel)
        mv.logE("sss", "hook click")
        mv.visitInsn(Opcodes.RETURN)
        mv.visitLabel(elseFalseLabel)
        mv.visitFieldInsn(
            GETSTATIC,
            "com/syc/mvvm/track/Tracker",
            "INSTANCE",
            "Lcom/syc/mvvm/track/Tracker;"
        )
        mv.visitVarInsn(ALOAD, 1)
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "com/syc/mvvm/track/Tracker",
            "onHookedClick",
            "(Landroid/view/View;)V",
            false
        )
        super.onMethodEnter()
    }
}