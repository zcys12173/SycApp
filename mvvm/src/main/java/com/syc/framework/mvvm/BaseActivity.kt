package com.syc.framework.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.syc.framework.mvvm.viewmodel.VMContainer
import com.syc.framework.mvvm.viewmodel.VMManager

/**
 * Created by shiyucheng on 2019-11-29.
 * Activity 基类
 * 主要负责触发ViewModelManager的各个生命周期函数
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    VMContainer {
    override val vmManager: VMManager = VMManager()
    protected lateinit var rootBinding: T
      private set//限制子类不可修改

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        //必须放到 vmManager.onCreate(savedInstanceState）前面，否则ViewModel的onCreate()不会执行
        initViewModel(savedInstanceState)
        vmManager.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    /**
     *  获取activity布局资源id
     *  @return Activity layout resource id
     */
    protected abstract fun getLayoutResId(): Int

    /**
     * 初始化ViewModel 优先于 {@link init(savedInstanceState)} 执行
     */
    protected abstract fun initViewModel(savedInstanceState: Bundle?)

    /**
     * 初始化 在 {@link onCreate(savedInstanceState)} 最后执行
     */
    protected abstract fun init(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        vmManager.onStart()
    }
    override fun onResume() {
        super.onResume()
        vmManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        vmManager.onPause()
    }

    override fun onStop() {
        super.onStop()
        vmManager.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        vmManager.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vmManager.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        vmManager.onRestoreInstanceState(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        vmManager.onNewIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vmManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        vmManager.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    override fun getActivity() = this
}