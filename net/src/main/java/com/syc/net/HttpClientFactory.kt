package com.syc.net

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HttpClientFactory() {
    companion object {
        @JvmStatic
        fun create(config: HttpConfig) =
            OkHttpClient().newBuilder().apply {
                callTimeout(config.timeout, TimeUnit.MILLISECONDS)
                hostnameVerifier(config.hostnameVerifier)
                config.interceptors.forEach {
                    addInterceptor(it)
                }
                config.networkInterceptor.forEach {
                    addNetworkInterceptor(it)
                }
                config.certs.takeIf { it.isNotEmpty() }?.let {
                    val sslFactoryAndTrustManager = createSslSocketFactoryAndTrustManager(it.toTypedArray())
                    this.sslSocketFactory(sslFactoryAndTrustManager.sslSocketFactory,sslFactoryAndTrustManager.trustManager)
                }
            }.build()
    }
}





