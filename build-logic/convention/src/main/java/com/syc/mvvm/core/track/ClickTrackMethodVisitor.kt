package com.syc.mvvm.core.track

import com.syc.mvvm.core.ASM_VERSION
import com.syc.mvvm.core.logE
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter


/**
 * create by syc, 2024/7/10
 */
class ClickTrackMethodVisitor(mv: MethodVisitor, access: Int, name: String?, descriptor: String?) :
    AdviceAdapter(ASM_VERSION, mv, access, name, descriptor) {
    override fun onMethodEnter() {
        mv.logE("sss","hook click")
        mv.visitInsn(Opcodes.RETURN)
        super.onMethodEnter()
    }
}