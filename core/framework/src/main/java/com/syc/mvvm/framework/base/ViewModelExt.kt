package com.syc.mvvm.framework.base

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import java.util.*


/**
 * Created by shiyucheng on 2021/5/17.
 * ViewModel扩展方法
 *
 * class TestActivity : SpActivity(){
 *    override fun onCreate(savedInstanceState: Bundle?){
 *      //使用createViewModel创建ViewModel
 *      val viewModel = createViewModel(this,TestViewModel::class.java)
 *    }
 *
 *    fun test(){
 *      Log.d("TestActivity","test invoke")
 *    }
 * }
 *
 * class TestFragment : Fragment(){
 *    override fun onCreate(savedInstanceState: Bundle?){
 *      //使用createViewModel创建ViewModel
 *      val viewModel = createViewModel(this,TestViewModel::class.java)
 *    }
 *
 *    fun test(){
 *      Log.d("TestFragment","test invoke")
 *    }
 * }
 *
 * class TestViewModel(context:Context) : AndroidViewModel(context){
 *
 *     private fun testActivityMethod(){
 *          getActivity<TestActivity>()?.test()
 *     }
 *
 *     private fun testFragmentMethod(){
 *          getFragment<TestFragment>()?.test()
 *     }
 * }
 */

private const val TAG_KEY_STORE_DELEGATE = "store_delegate_key"


private fun ViewModel.getVmStoreDelegate(): ViewModelStoreDelegate? {
    return getTag(TAG_KEY_STORE_DELEGATE) as? ViewModelStoreDelegate
}

/**
 * 创建ViewModel 替换ViewModelProvider
 */
fun <T : ViewModel> createViewModel(
    owner: ViewModelStoreOwner,
    modelClass: Class<T>,
    key: String = modelClass.toKey(),
    replace: Boolean = false
): T {
    if (replace) {
        owner.removeViewModel(key)
    }
    return ViewModelProvider(owner).get(key, modelClass).apply {
        val delegate = ViewModelStoreDelegate(owner)
        removeTag(TAG_KEY_STORE_DELEGATE)//移除老的delegate（适用于Activity异常销毁后页面恢复场景）
        setTagIfAbsent(TAG_KEY_STORE_DELEGATE, delegate)
        if (owner is LifecycleOwner && this is LifecycleObserver) {
            owner.lifecycle.addObserver(this)
        }
    }
}

/**
 *  获取同Scope下的ViewModel
 */
fun <T : ViewModel> ViewModel.findViewModel(modelClass: Class<T>): T? {
    return getVmStoreDelegate()?.findViewModel(modelClass)
}

/**
 *  获取同Scope下的ViewModel
 */
fun <T : ViewModel> ViewModel.findViewModel(key: String): T? {
    return getVmStoreDelegate()?.findViewModel(key)
}

val ViewModel.activity : Activity?
    get() = getVmStoreDelegate()?.getActivity()

/**
 * 关闭Activity
 */
fun ViewModel.finishActivity() {
    activity?.finish()
}

/**
 * 启动Activity
 */
fun ViewModel.startActivity(intent: Intent) {
    activity?.startActivity(intent)
}

/**
 * 启动Activity
 */
fun ViewModel.startActivityForResult(intent: Intent, requestCode: Int) {
    activity?.startActivityForResult(intent, requestCode)
}

/**
 * 获取容器Fragment
 */
fun <T : Fragment> ViewModel.getFragment(): T? {
    return getVmStoreDelegate()?.getFragment()
}

/**
 * 通过反射调用androidx.lifecycle.ViewModel.setTagIfAbsent(key: String, value: T)
 * 因为是反射实现，故此方法不对外公开，防止侵入业务太多,不易维护
 */
private fun <T> ViewModel.setTagIfAbsent(key: String, value: T) {
    val setMethod = ViewModel::class.java.getDeclaredMethod(
        "setTagIfAbsent",
        String::class.java,
        Any::class.java
    )
    setMethod.isAccessible = true
    setMethod.invoke(this, key, value)
}

/**
 * 通过反射调用androidx.lifecycle.ViewModel.getTag(key: String)
 * 因为是反射实现，故此方法不对外公开，防止侵入业务太多,不易维护
 */
private fun <T> ViewModel.getTag(key: String): T? {
    val setMethod = ViewModel::class.java.getDeclaredMethod("getTag", String::class.java)
    setMethod.isAccessible = true
    return setMethod.invoke(this, key) as? T
}


/**
 * 通过反射获取变量mBagOfTags移除相关tag
 * 因为是反射实现，故此方法不对外公开，防止侵入业务太多,不易维护
 */
private fun ViewModel.removeTag(key: String) {
    val field = ViewModel::class.java.getDeclaredField("mBagOfTags")
    field.isAccessible = true
    val map = field.get(this) as? MutableMap<*, *>
    map?.remove(key)
}

private fun ViewModelStoreOwner.removeViewModel(key: String) {
    val field = ViewModelStore::class.java.getDeclaredField("mMap")
    field.isAccessible = true
    val map = field.get(viewModelStore) as? HashMap<String, ViewModel>
    val vm = map?.remove(key)
    vm?.run {
        val cleanMethod = ViewModel::class.java.getDeclaredMethod("clear")
        cleanMethod.isAccessible = true
        cleanMethod.invoke(this)
    }
}


