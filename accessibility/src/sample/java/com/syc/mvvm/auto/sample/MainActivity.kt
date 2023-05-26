package com.syc.mvvm.auto.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.syc.mvvm.net.Api
import com.syc.mvvm.net.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Api.init("https://developer.android.google.cn/")
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv).setOnClickListener {
            GlobalScope.launch {
                Api.retrofit.create(TestApi::class.java).test("zh-cn")
            }
        }
    }
}

interface TestApi{
    @GET("docs")
    suspend fun test(@Query("hl") params:String)
}

