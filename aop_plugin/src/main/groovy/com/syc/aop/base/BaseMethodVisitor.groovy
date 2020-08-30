package com.syc.aop.base

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

public abstract class BaseMethodVisitor extends AdviceAdapter{

    protected BaseMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor)
    }

    public abstract boolean match(int access, String name, String descriptor, String signature, String[] exceptions)
}