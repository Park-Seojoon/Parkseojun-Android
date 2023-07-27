package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.response.LoginResponse
import com.seojunpark.android.data.dto.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}