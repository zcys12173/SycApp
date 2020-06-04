package com.syc.mvvm.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.syc.mvvm.mvvm.viewmodel.VMContainer
import com.syc.mvvm.mvvm.viewmodel.VMManager

/**
 * Fragment 基础类
 * 主要负责触发ViewModelManager的各个生命周期函数
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(),
    VMContainer {
    override val vmManager: VMManager = VMManager()

    protected lateinit var rootBinding: T
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), null, false)
        initViewModel(savedInstanceState)
        vmManager.onCreate(savedInstanceState)
        init(savedInstanceState)
        return rootBinding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        vmManager.onDestroy()
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
        vmManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vmManager.onSaveInstanceState(outState)
    }
}