package com.syc.aop.transform

import com.quinn.hunter.transform.asm.BaseWeaver
import com.syc.aop.visitor.ScanClassVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class AopWeaver extends BaseWeaver {

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new ScanClassVisitor(classWriter)
    }
}