package com.syc.qrcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.zxing.client.android.CaptureActivity

class MainActivity:Activity() {
    private lateinit var iv :QRCodeImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv = findViewById(R.id.iv_qrcode)
        findViewById<Button>(R.id.btn_create_qrcode).setOnClickListener {
            iv.setText("jkajdflkajfkd")
            startActivity(Intent(this@MainActivity,
                CaptureActivity::class.java))
        }
    }
}