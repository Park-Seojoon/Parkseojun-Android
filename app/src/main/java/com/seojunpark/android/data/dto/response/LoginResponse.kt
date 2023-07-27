package com.seojunpark.android.data.dto.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiredAt: String,
    val refreshExpiredAT: String
)
