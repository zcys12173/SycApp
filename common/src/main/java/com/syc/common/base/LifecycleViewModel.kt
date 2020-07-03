package com.syc.common.base

import android.os.Bundle
import androidx.annotation.CallSuper
import com.syc.mvvm.mvvm.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * 将协程于生命周期绑定的ViewModel
 * 在ViewModel销毁的时候会自动取消协程任务
 * @sample {
 *  launch {
 *   val response = withContext(Dispatchers.IO){
 *   getDataFromNet() //从网络获取数据
 *   }
 *   showView(response)  //将数据渲染到view上
 *   }
 * }
 */
open class LifecycleViewModel(@JvmField private val exceptionHandler: CoroutineExceptionHandler? = null) :
    BaseViewModel(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + (exceptionHandler
            ?: CoroutineExceptionHandler { coroutineContext, exception ->
                handleCoroutineException(coroutineContext, exception)
                return@CoroutineExceptionHandler
            })

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    @CallSuper
    override fun onDestroy() {
        job.takeIf { it.isActive }?.run {
            cancel()
        }
        super.onDestroy()
    }

    /**
     * 协程错误处理
     */
    protected open fun handleCoroutineException(context: CoroutineContext, exception: Throwable) {}


}
