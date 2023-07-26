package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.LoginDTO
import com.seojunpark.android.data.dto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginDTO>
}