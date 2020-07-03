package com.syc.net

import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

fun createSslSocketFactoryAndTrustManager(
    certificates: Array<InputStream>,
    bksFile: InputStream? = null,
    password: String? = null
): SslFactoryAndTrustManager {
    val trustManager = MyTrustManager(prepareTrustManager(certificates))
    val keyManagers = prepareKeyManager(bksFile, password)
    val sslSocketFactory = SSLContext.getInstance("TLS").apply {
        init(keyManagers, arrayOf(), SecureRandom())
    }.socketFactory
    return SslFactoryAndTrustManager(sslSocketFactory, trustManager)
}

private fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
    if (bksFile == null || password == null) {
        return null
    }
    try {
        val clientKeyStore = KeyStore.getInstance("BKS").apply {
            load(bksFile, password.toCharArray())
        }
        val keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).apply {
                init(clientKeyStore, password.toCharArray())
            }
        return keyManagerFactory.keyManagers
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

private fun prepareTrustManager(certificates: Array<InputStream>): Array<X509TrustManager>? {
    val result = mutableListOf<X509TrustManager>()
    certificates.forEachIndexed { index, inputStream ->
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null)
                setCertificateEntry(
                    index.toString(),
                    certificateFactory.generateCertificate(inputStream)
                )
            }
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                    init(keyStore)
                }
            val trustManager = chooseX509TrustManager(trustManagerFactory.trustManagers)
            trustManager?.run {
                result.add(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return result.toTypedArray()
}

private fun chooseX509TrustManager(trustManagerArray: Array<TrustManager>): X509TrustManager? {
    val result = trustManagerArray.find { it is X509TrustManager }
    return if (result == null) {
        result
    } else {
        result as X509TrustManager
    }

}

class MyTrustManager(private val localTrustManagerArray: Array<X509TrustManager>?) :
    X509TrustManager {

    private val defaultTrustManager by lazy(LazyThreadSafetyMode.NONE) {
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        chooseX509TrustManager(trustManagerFactory.trustManagers)
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        localTrustManagerArray?.forEach {
            try {
                it.checkServerTrusted(chain, authType)
                return
            } catch (e: Exception) {
                // 尝试使用本地公钥检查,无需处理校验异常
            }
        }
        // 本地公钥都不适用,请求CA校验
        defaultTrustManager?.checkServerTrusted(chain, authType)
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return emptyArray()
    }
}

val defaultHostnameVerifier = HostnameVerifier { hostname, session -> true }

data class SslFactoryAndTrustManager(
    val sslSocketFactory: SSLSocketFactory,
    val trustManager: X509TrustManager
)
