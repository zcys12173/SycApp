package com.syc.common.base

/**
 * 网络返回数据外层包装类
 */
data class BaseResponse<T>(var code:Int,var errMsg:String,var data:T)