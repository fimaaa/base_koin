package com.example.basekotlinkoin.data.repository

import com.example.basekotlinkoin.data.base.BaseResponse
import com.example.basekotlinkoin.data.model.RefreshToken
import com.example.basekotlinkoin.data.source.endpoint.ApiServiceUserBasic

class ApiRepoUsersBasic(private val apiService: ApiServiceUserBasic) {

    suspend fun getRefreshToken(refreshToken: String?): BaseResponse<RefreshToken> {
        return apiService.refreshTokenAsync(refreshToken).await()
    }
}