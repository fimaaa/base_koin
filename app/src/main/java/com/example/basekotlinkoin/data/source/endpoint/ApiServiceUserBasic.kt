package com.example.basekotlinkoin.data.source.endpoint

import com.example.basekotlinkoin.data.base.BaseResponse
import com.example.basekotlinkoin.data.model.RefreshToken
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiServiceUserBasic {
    @POST("users/v1/refresh-token")
    @FormUrlEncoded
    fun refreshTokenAsync(
        @Field("refreshToken") refreshToken: String?
    ): Deferred<BaseResponse<RefreshToken>>
}