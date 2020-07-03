package com.syc.net

import okhttp3.Interceptor
import java.io.InputStream
import javax.net.ssl.HostnameVerifier

/**
 * Http 配置类
 */
data class HttpConfig(var timeout:Long = 20000,
                      var baseUrl:String = "",
                      var certs:MutableList<InputStream> = mutableListOf(),
                      var hostnameVerifier:HostnameVerifier = defaultHostnameVerifier,
                      var interceptors: MutableList<Interceptor> = mutableListOf(),
                      var networkInterceptor: MutableList<Interceptor> = mutableListOf())