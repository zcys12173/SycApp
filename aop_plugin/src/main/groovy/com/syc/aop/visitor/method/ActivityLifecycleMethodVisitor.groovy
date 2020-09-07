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
        mv.visitFieldInsn(GETSTATIC,"com/syc/track/LifecycleTracker","INSTANCE","Lcom/syc/track/LifecycleTracker;");
        mv.visitVarInsn(ALOAD,0)
        if(name == "onStart"){
            mv.visitMethodInsn(INVOKEVIRTUAL,"com/syc/track/LifecycleTracker","onStart","(Ljava/lang/Object;)V",false);
        }

        if(name == "onStop"){
            mv.visitMethodInsn(INVOKEVIRTUAL,"com/syc/track/LifecycleTracker","onStop","(Ljava/lang/Object;)V",false);
        }


//        GETSTATIC com/syc/track/LifecycleTracker.INSTANCE : Lcom/syc/track/LifecycleTracker;
//        LDC "sss"
//        INVOKEVIRTUAL com/syc/track/LifecycleTracker.onStart (Ljava/lang/String;)V
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode)
    }

}