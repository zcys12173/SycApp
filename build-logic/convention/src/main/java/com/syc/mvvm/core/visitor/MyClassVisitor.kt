package com.syc.mvvm.core.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

class MyClassVisitor(visitor: ClassVisitor):ClassVisitor(Opcodes.ASM7,visitor){
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
    }
}