package com.syc.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.syc.common.base.LifecycleViewModel
import com.syc.mvvm.mvvm.viewmodel.BaseViewModel
import com.syc.net.HttpManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel : LifecycleViewModel(){
    var name = "我的名字"

    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.d("TestViewModel", "onTextChanged")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TestViewModel",BuildConfig.URL)
        mockNetRequest()
    }


    private fun mockNetRequest(){
        launch {
            val result = withContext(Dispatchers.IO) {
                HttpManager.instance.createService(ApiService::class.java).login("shiyucheng1","shiyucheng1")
            }
            this@TestViewModel.name = result.data.userName?:""
            notifyChange()
        }
    }

    fun openMainFragmentActivity() {
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .takeIf { it == PERMISSION_GRANTED }?.run {
                startFragmentActivity()
            } ?: run {
            requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.takeIf { !it.contains(PERMISSION_DENIED) && requestCode == 1 }?.run {
            startFragmentActivity()
        }
    }

    private fun startFragmentActivity(){
        startActivityForResult(Intent(getContext(), MainFragmentActivity::class.java),2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        takeIf { requestCode == 2 && resultCode == Activity.RESULT_OK }?.run {
            Toast.makeText(getContext(),"跳转成功了",Toast.LENGTH_SHORT).show()
        }
    }

    fun close(){
        setResult(Activity.RESULT_OK)
        finish()
    }
}
