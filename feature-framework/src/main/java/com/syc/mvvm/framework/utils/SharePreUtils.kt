package com.syc.mvvm.framework.utils

import android.os.Parcelable
import android.util.Log
import com.syc.mvvm.framework.base.application
import com.syc.mvvm.framework.json.gson
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel

var SP_LOGGABLE = true

private const val DEFAULT_MMAP_ID = "default_mmap_id"

private var hasInit = false

private val mmkvCacheMap = mutableMapOf<String, MMKV>()

private fun checkInit() {
    if (!hasInit) {
        val rootDir = MMKV.initialize(application)
        if(SP_LOGGABLE){
            MMKV.setLogLevel(MMKVLogLevel.LevelInfo)
        }
        Log.e("MMKV", "mmkv root: $rootDir")
        hasInit = true
    }
}

private fun getMMKV(id: String = DEFAULT_MMAP_ID, multiProcess: Boolean = true): MMKV {
    checkInit()
    val mode = if (multiProcess) MMKV.MULTI_PROCESS_MODE else MMKV.SINGLE_PROCESS_MODE
    return mmkvCacheMap[id] ?: MMKV.mmkvWithID(id, mode)
        .apply {
            mmkvCacheMap[id] = this
        }
}

/**
 * 保存对象到SP
 * @param key 键
 * @param id SP的id  默认为[DEFAULT_MMAP_ID]
 * @param multiProcess 是否支持多进程
 */
fun Any.saveToSp(key: String, id: String = DEFAULT_MMAP_ID, multiProcess: Boolean = true) {
    getMMKV(id, multiProcess).apply {
        when (this@saveToSp) {
            is Long -> encode(key, this@saveToSp)
            is String -> encode(key, this@saveToSp)
            is Int -> encode(key, this@saveToSp)
            is Boolean -> encode(key, this@saveToSp)
            is Float -> encode(key, this@saveToSp)
            is Parcelable -> encode(key, this@saveToSp)
            else -> gson.toJson(this@saveToSp).apply {
                encode(key, this)
            }
        }
    }
}

/**
 * 从SP中获取对象
 * @param key 键
 * @param defaultValue 默认值
 * @param id SP的id  默认为[DEFAULT_MMAP_ID]
 */
fun <T> getFromSp(key: String, defaultValue: T, id: String = DEFAULT_MMAP_ID): T {
    return getMMKV(id).run {
        when (defaultValue) {
            is Long -> decodeLong(key, defaultValue)
            is String -> decodeString(key, defaultValue)
            is Int -> decodeInt(key, defaultValue)
            is Boolean -> decodeBool(key, defaultValue)
            is Float -> decodeFloat(key, defaultValue)
            is Parcelable -> decodeParcelable(key, defaultValue!!::class.java)
            else -> decodeString(key, "").run {
                if (this.isNullOrEmpty()) {
                    defaultValue
                } else {
                    gson.fromJson(this, defaultValue!!::class.java)
                }
            }
        }
    } as T

}


/**
 * 从SP中删除对象
 * @param keys 键
 * @param id SP的id  默认为[DEFAULT_MMAP_ID]
 */
fun deleteFromSp(vararg keys: String, id: String = DEFAULT_MMAP_ID){
    when(keys.size){
        0 -> {/* do nothing */}
        1 -> getMMKV(id).removeValueForKey(keys.first())
        else -> getMMKV(id).removeValuesForKeys(keys)
    }
}