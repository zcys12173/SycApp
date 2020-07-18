package com.syc.aop.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

class ScanClassVisitor extends ClassVisitor{

    ScanClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor)
        println("ScanClassVisitor---create");
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        println("ScanClassVisitor---$name")
    }
}