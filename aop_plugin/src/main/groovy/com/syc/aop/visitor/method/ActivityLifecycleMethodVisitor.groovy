package com.syc.aop.visitor.method

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class ActivityLifecycleMethodVisitor extends AdviceAdapter{

    public ActivityLifecycleMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM6, methodVisitor, access, name, descriptor)
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
    }

}