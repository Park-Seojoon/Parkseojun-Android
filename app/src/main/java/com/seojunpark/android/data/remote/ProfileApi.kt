package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {

    @GET("user/info")
    suspend fun userInfo(
        @Header("Authorization") accessToken: String
    ): Response<ProfileResponse>
}