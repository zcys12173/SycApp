package com.syc.app

import com.syc.common.base.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("userName") userName: String,
        @Field("password") password: String
    ): BaseResponse<User>
}
