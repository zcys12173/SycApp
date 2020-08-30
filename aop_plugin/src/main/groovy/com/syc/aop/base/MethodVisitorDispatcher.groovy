package com.syc.aop.base

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

import java.lang.reflect.Method

final class MethodVisitorDispatcher{

    private ClassVisitor cv;

    MethodVisitorDispatcher(ClassVisitor cv) {
        this.cv = cv
    }
    private List<Class<? extends BaseMethodVisitor>> methodVisitors = new ArrayList<>();

    public boolean addMethodVisitor(Class<? extends BaseMethodVisitor> clazz){
        return methodVisitors.add(clazz)
    }


    public boolean removeMethodVisitor(Class<? extends BaseMethodVisitor> clazz){
        return methodVisitors.remove(clazz)
    }

    public BaseMethodVisitor dispatch(int access, String name, String descriptor, String signature, String[] exceptions){
        methodVisitors.find {
            Method method = it.getMethod();
            method.invokeMethod()
            it.match(access,name,descriptor,signature,exceptions)
        }
    }
}

