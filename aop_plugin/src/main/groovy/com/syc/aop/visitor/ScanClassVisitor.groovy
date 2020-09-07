package com.syc.aop.visitor

import com.syc.aop.base.MethodVisitorDispatcher
import com.syc.aop.visitor.method.ActivityLifecycleMethodVisitor
import com.syc.aop.visitor.method.ViewClickMethodVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ScanClassVisitor extends ClassVisitor {
    private boolean isOnclickClass;
    private MethodVisitorDispatcher dispatcher;

    ScanClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor)
        dispatcher = new MethodVisitorDispatcher(this);
        println("ScanClassVisitor---create")
    }

    private void registerMethodVisitor(){
//        dispatcher.addMethodVisitor(new ViewClickMethodVisitor())
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        if (interfaces.contains("android/view/View\$OnClickListener")) {
            isOnclickClass = true
        }
    }


    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access,name,descriptor,signature,exceptions)
        if(isOnclickClass){
            StringBuilder sb = new StringBuilder("[")
            exceptions.each {
                sb.append(it)
                sb.append(",")
            }
            sb.append("]")
            println("visitMethod : access=$access,name=$name,descriptor=$descriptor,signature=$signature,exexceptions=${sb.toString()}")
            if(name == "onClick"){
                println('find onclick method')
                if(descriptor == "(Landroid/view/View;)V"){
                    println('make sure is onclick')
                    mv = new ViewClickMethodVisitor(mv,access,name,descriptor)
                }
            }
        }

        if((name == "onStart" || name == "onStop" ) && descriptor == "()V"){
            mv = new ActivityLifecycleMethodVisitor(mv,access,name,descriptor);
        }

        return mv
    }
}