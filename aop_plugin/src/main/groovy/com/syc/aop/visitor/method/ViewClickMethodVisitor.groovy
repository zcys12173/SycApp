package com.syc.aop.visitor.method

import com.syc.aop.base.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ViewClickMethodVisitor extends BaseMethodVisitor {

    ViewClickMethodVisitor(
            final MethodVisitor methodVisitor,
            final int access,
            final String name,
            final String descriptor) {
        super(Opcodes.ASM6, methodVisitor, access, name, descriptor)
    }


    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESTATIC, "com/syc/track/TrackHelper", "track", "(Landroid/view/View;)V", false);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("sss");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    @Override
    boolean match(int access, String name, String descriptor, String signature, String[] exceptions) {
        return name == "onClick" && descriptor == "(Landroid/view/View;)V";
    }
}