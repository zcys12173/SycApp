package com.syc.framework.mvvm.viewmodel

import android.app.Activity

interface VMContainer : IActivityStarter {
    val vmManager: VMManager

    /**
     * 绑定ViewModel到当前的Activity
     */
    fun bindViewModel(vm: BaseViewModel){
        vm.setActivityStarter(this)
        getActivity()?.run {
            vm.setActivity(this)
        }
        vmManager.addViewModel(vm)
    }
    /**
     * 从当前的Activity中解绑ViewModel
     */
    fun removeViewModel(vm: BaseViewModel){
        vmManager.removeViewModel(vm)
    }

    fun getActivity(): Activity?
}