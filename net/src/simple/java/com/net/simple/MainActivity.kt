package com.net.simple

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.syc.net.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, t ->
            Log.e("MainActivity", "a error is happened")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()
        val tv = findViewById<TextView>(R.id.tv)
        val s = "abbbbbba"
        val spannable = SpannableString(s)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "onclick", Toast.LENGTH_SHORT).show()
            }

        }, 0, s.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorPrimary)),
            0, s.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        tv.text = spannable
        tv.movementMethod = LinkMovementMethod.getInstance()
        request()
    }

    fun onStart(view: View) {
        request()
    }

    fun onCancel(view: View) {
        job.cancel()
    }

    fun request() {
        launch(CoroutineName("test")+CoroutineExceptionHandler { _, t ->
            Log.e("MainActivity", "a error is happened")
        }) {
            withContext(Dispatchers.IO){
                delay(5000)
                Log.d("MainActivity", "finish request 1")
            }
            Toast.makeText(this@MainActivity, "ssss1", Toast.LENGTH_SHORT).show()
            Log.d("MainActivity", "finish request 11")
        }
    }

    fun request1(){
        launch(CoroutineName("test")) {
            Log.d("MainActivity", Thread.currentThread().name)
            val rq = async(Dispatchers.IO, start = CoroutineStart.LAZY) {
                delay(5000)
                Log.d("MainActivity", "finish request 2")
            }
            rq.await()
            Toast.makeText(this@MainActivity, "ssss2", Toast.LENGTH_SHORT).show()
        }
    }
}