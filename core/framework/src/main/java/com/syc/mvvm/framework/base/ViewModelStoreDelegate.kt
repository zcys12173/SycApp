package com.syc.mvvm.framework.base

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.io.Closeable
import java.lang.ref.WeakReference

/**
 * Created by shiyucheng on 2021/9/6.
 * ViewModelStoreOwner委托类
 */
class ViewModelStoreDelegate(storeOwner: ViewModelStoreOwner) : Closeable {
    private val storeOwnerRef = WeakReference(storeOwner)

    fun <T : Activity> getActivity(): T?{
        val owner =  storeOwnerRef.get()
        return owner?.run {
            when(this){
                is Activity -> owner as? T
                is Fragment -> this.activity as? T
                else -> null
            }
        }
    }

    fun <T : Fragment> getFragment(): T? = storeOwnerRef.get() as? T

    fun getLifecycle():Lifecycle?{
        return (storeOwnerRef.get() as? LifecycleOwner)?.lifecycle
    }

    fun <T : ViewModel>findViewModel(modelClass: Class<T>):T?{
        return storeOwnerRef.get()?.viewModelStore?.get(modelClass)
    }

    fun <T : ViewModel>findViewModel(key:String):T?{
        return storeOwnerRef.get()?.viewModelStore?.get(key) as? T
    }

    override fun close() {
        getLifecycle()?.run {

        }
        storeOwnerRef.clear()
    }
}

/**
 * 通过反射调用ViewModelStore.put(String key, ViewModel viewModel)方法添加ViewModel
 * 因为是反射实现，故此方法不对外公开，防止侵入业务太多,不易维护
 */
private fun ViewModelStore.put(viewModel: ViewModel, key:String = viewModel.javaClass.toKey()){
    val putMethod = ViewModelStore::class.java.getDeclaredMethod("put",
        String::class.java,
        ViewModel::class.java)
    putMethod.isAccessible = true
    putMethod.invoke(this,key,viewModel)
}


/**
 * 通过反射调用ViewModelStore.get(String key)方法添加ViewModel
 * 因为是反射实现，故此方法不对外公开，防止侵入业务太多,不易维护
 */
private fun <T:ViewModel> ViewModelStore.get(key:String):T?{
    val getMethod = ViewModelStore::class.java.getDeclaredMethod("get", String::class.java)
    getMethod.isAccessible = true
    return getMethod.invoke(this,key) as? T
}

/**
 * 通过反射调用ViewModelStore.get(String key)方法添加ViewModel
 *  因为是反射实现，故此方法不对外公开，防止侵入业务太多,不易维护
 */
private fun <T:ViewModel> ViewModelStore.get(viewModelClass:Class<T>):T?{
    val key = viewModelClass.toKey()
    return get(key) as? T
}

fun Class<*>.toKey(): String {
    return name
}