package com.syc.framework.mvvm.viewmodel

interface VMObserver {
    fun addViewModel(vm: BaseViewModel): BaseViewModel?

    fun removeViewModel(vm: BaseViewModel): BaseViewModel?
}
