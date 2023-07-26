package com.seojunpark.android.data.dto

import retrofit2.http.Body

data class LoginRequest(
    val email: String,
    val password: String
)
