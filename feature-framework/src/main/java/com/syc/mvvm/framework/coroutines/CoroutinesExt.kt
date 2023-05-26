package com.syc.mvvm.framework.coroutines

import android.app.Dialog
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.IllegalStateException

private const val DIALOG_SCOPE_TAG = -10
private const val VIEW_SCOPE_TAG = -11

/**
 * Dialog的协程作用域
 */
val Dialog.dialogScope: CoroutineScope
    get() {
        return window?.decorView?.getTag(DIALOG_SCOPE_TAG) as? CoroutineScope ?: CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        ).apply scope@{
            window?.decorView?.run {
                setTag(DIALOG_SCOPE_TAG, this@scope)
                addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                    override fun onViewAttachedToWindow(v: View) {
                    }

                    override fun onViewDetachedFromWindow(v: View) {
                        cancel("Dialog is dismissed",IllegalStateException("Dialog is dismissed"))
                    }
                })
            }
        }
    }


/**
 * View的协程作用域
 */
val View.viewScope:CoroutineScope
    get() {
        return getTag(VIEW_SCOPE_TAG) as? CoroutineScope ?: CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        ).apply scope@{
            setTag(VIEW_SCOPE_TAG, this@scope)
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                }

                override fun onViewDetachedFromWindow(v: View) {
                    cancel("View is detached",IllegalStateException("View is detached"))
                }
            })
        }
    }