package com.syc.mvvm.net

import com.syc.mvvm.framework.json.gson
import retrofit2.Retrofit

object Api {
    lateinit var retrofit: Retrofit

    fun init(baseUrl:String){
        createRetrofit(baseUrl)
    }

    private fun createRetrofit(baseUrl:String){
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ConvertFactory(gson))
            .client(createOkHttpClient())
            .build()
    }
}