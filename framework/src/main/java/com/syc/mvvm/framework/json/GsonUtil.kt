package com.syc.mvvm.framework.json

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by shiyucheng on 2021/11/18.
 * Gson 全局通用配置
 */

/**
 * 全局单例Gson对象
 */
val gson by lazy {
    buildGson()
}

/**
 * 创建Gson
 */
fun buildGson(): Gson {
    return GsonBuilder()
        .addSerializationExclusionStrategy(serializationStrategy)
        .addDeserializationExclusionStrategy(deserialization)
        .create()
}

/**
 * 序列化过滤策略
 */
private val serializationStrategy = object : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        return f?.let { field ->
            val ignore = field.getAnnotation(Ignore::class.java)
            return ignore?.serialize ?: false
        } ?: true
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false
}

/**
 * 反序列化过滤策略
 */
private val deserialization = object : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        return f?.let { field ->
            val ignore = field.getAnnotation(Ignore::class.java)
            return ignore?.deserialize ?: false
        } ?: true
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false
}



/**
 * Created by shiyucheng on 2021/11/18.
 * Gson 序列化/反序列化 字段忽略
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Ignore(
    val serialize: Boolean = true, // 序列化的时候忽略该字段
    val deserialize: Boolean = true// 反序列化的时候忽略该字段
)


