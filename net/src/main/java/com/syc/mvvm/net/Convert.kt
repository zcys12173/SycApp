package com.syc.mvvm.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ConvertFactory(private val gson: Gson = Gson()) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return ResponseBodyConverter<Any>(type)
    }


    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return RequestConvert<Any>()
    }

    inner class RequestConvert<T> : Converter<T, RequestBody> {
        private val mediaType = "application/json".toMediaType()
        override fun convert(value: T): RequestBody {
            return gson.toJson(value).toRequestBody(mediaType)
        }
    }

    inner class ResponseBodyConverter<T>(private val type: Type) : Converter<ResponseBody, T> {
        override fun convert(value: ResponseBody): T? {
            val typeToken = object : TypeToken<HttpEntity<String>>() {}.type
            val baseEntity = gson.fromJson<HttpEntity<String>>(value.string(), typeToken)
            if(baseEntity.code != 0){
                throw ServerRespondException(baseEntity.code,baseEntity.message)
            }
            return gson.fromJson(baseEntity.data, type)
        }
    }
}

