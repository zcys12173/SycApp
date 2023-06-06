package com.syc.mvvm.permission



interface Interceptor {
    fun intercept(chain: Chain)

    class Chain(private val interceptors: List<Interceptor>) {
        private var index = -1
        lateinit var request: Permission.Request
            private set

        fun process(request: Permission.Request) {
            this.request = request
            interceptors.getOrNull(++index)?.intercept(this)
        }
    }
}

