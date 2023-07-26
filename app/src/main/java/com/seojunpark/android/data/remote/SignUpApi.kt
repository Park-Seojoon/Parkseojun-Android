package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {

    @POST("auth/signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<Unit>
}