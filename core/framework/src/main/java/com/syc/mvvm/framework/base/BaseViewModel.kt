package com.syc.mvvm.framework.base

import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {
    /**
     * 当 [BaseActivity] 绑定到 [BaseViewModel]的时候调用
     * 一般在createViewModel()的函数调用的时候执行
     * 这个方法执行后Activity一定不为空
     */
    open fun onAttach(){}
}