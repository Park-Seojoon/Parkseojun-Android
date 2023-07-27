package com.seojunpark.android.data.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessExpiredAt: String,
    val refreshExpiredAT: String
)
