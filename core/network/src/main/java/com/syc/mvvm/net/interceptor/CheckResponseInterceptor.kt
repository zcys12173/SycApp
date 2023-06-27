package com.syc.mvvm.net.interceptor

import com.syc.mvvm.framework.json.gson
import com.syc.mvvm.net.HttpEntity
import com.syc.mvvm.net.ServerResponseException
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 检测服务端返回code是否为0
 */
class CheckResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        response.body?.run {
            val json = string()
            gson.fromJson(json, HttpEntity::class.java).run {
                if (code != 0) {
                    val dataStr = gson.toJson(data)
                    throw ServerResponseException(code, data = dataStr, message = message)
                }
            }
            val mediaType = "application/json".toMediaType()
            return response.newBuilder().body(json.toResponseBody(mediaType)).build()
        }
        return response
    }
}