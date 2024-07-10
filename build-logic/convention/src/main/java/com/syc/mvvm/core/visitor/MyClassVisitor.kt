package com.syc.mvvm.core.visitor

import com.syc.mvvm.core.track.ActivityOnCreateMethodVisitor
import com.syc.mvvm.core.track.ClickTrackMethodVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MyClassVisitor(visitor: ClassVisitor):ClassVisitor(Opcodes.ASM7,visitor){
    private var isActivity = false
    private var className = ""
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        isActivity = name?.endsWith("Activity") == true &&  (access and Opcodes.ACC_FINAL) !== 0
        if(isActivity){
            className = name.orEmpty()
            println("hook class $className")
        }
        if(name?.contains("Tracker") == true){
            println("tracker == $name")
        }
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if(name == "onClick" && descriptor == "(Landroid/view/View;)V"){
            ClickTrackMethodVisitor(mv,access,name,descriptor)
        }else if(isActivity && name == "onCreate" && descriptor == "(Landroid/os/Bundle;)V"){
            ActivityOnCreateMethodVisitor(className,mv,access,name,descriptor)
        }else{
            mv
        }
    }
}