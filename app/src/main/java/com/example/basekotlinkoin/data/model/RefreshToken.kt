package com.example.basekotlinkoin.data.model

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?
)
