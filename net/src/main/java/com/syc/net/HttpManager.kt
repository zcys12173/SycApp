package com.syc.net

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpManager private constructor() {
    private var config: HttpConfig? = null
    private lateinit var appContext: Context
    private val retrofit by lazy {
        val builder = Retrofit.Builder()
        builder.addConverterFactory(GsonConverterFactory.create())
        config?.run {
            builder.baseUrl(baseUrl)
            builder.client(HttpClientFactory.create(this))
        }
        builder.build()
    }

    companion object {
        @JvmStatic
        val instance by lazy { HttpManager() }
    }

    fun init(context: Context, config: HttpConfig) {
        this.appContext = context.applicationContext
        this.config = config
    }


    fun <T> createService(clazz: Class<T>): T = retrofit.create(clazz)
}
