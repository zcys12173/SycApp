package com.syc.mvvm.mvvm.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap

/**
 * ViewModel 管理类
 * 集中管理ViewModel的生命周期函数调用
 */
class VMManager : VMLifecycle, VMObserver {
    private var lifecycleState: State = State.INITIALIZED

    private val viewModels by lazy { ArrayMap<String, BaseViewModel>() }

    override fun addViewModel(vm: BaseViewModel) = viewModels.put(vm::class.java.simpleName,vm)

    override fun removeViewModel(vm: BaseViewModel) = viewModels.remove(vm::class.java.simpleName)

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleState = State.CREATED
        traverseViewModels {
            it.onCreate(savedInstanceState)
            it.setLifecycleState(lifecycleState)
        }
    }

    override fun onStart() {
        lifecycleState = State.STARTED
        traverseViewModels {
            it.onStart()
            it.setLifecycleState(lifecycleState)
        }
    }

    override fun onResume() {
        lifecycleState = State.RESUMED
        traverseViewModels {
            it.onResume()
            it.setLifecycleState(lifecycleState)
        }
    }

    override fun onPause() {
        lifecycleState = State.PAUSED
        traverseViewModels {
            it.onPause()
            it.setLifecycleState(lifecycleState)
        }
    }

    override fun onStop() {
        lifecycleState = State.STOPPED
        traverseViewModels {
            it.onStop()
            it.setLifecycleState(lifecycleState)
        }
    }

    override fun onDestroy() {
        lifecycleState = State.DESTROYED
        traverseViewModels {
            it.onDestroy()
            it.setLifecycleState(lifecycleState)
        }
        release()
    }


    override fun onNewIntent(intent: Intent?) {
        traverseViewModels {
            it.onNewIntent(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        traverseViewModels {
            it.onSaveInstanceState(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        traverseViewModels {
            it.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        traverseViewModels {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        traverseViewModels {
            it.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * 遍历ViewModel集合并做相应处理
     * @param handler 处理函数
     */
    private inline fun traverseViewModels(handler: (vm: BaseViewModel) -> Unit) {
        viewModels.forEach {
            handler.invoke(it.value)
        }
    }

    private fun release(){
        viewModels.clear()
    }

}