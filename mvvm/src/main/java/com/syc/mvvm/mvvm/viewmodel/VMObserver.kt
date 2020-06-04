package com.syc.mvvm.mvvm.viewmodel

interface VMObserver {
    fun addViewModel(vm: BaseViewModel): BaseViewModel?

    fun removeViewModel(vm: BaseViewModel): BaseViewModel?
}
