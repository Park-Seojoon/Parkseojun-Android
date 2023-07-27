package com.seojunpark.android.data.dto.request

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val rePassword: String
)
