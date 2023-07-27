package com.seojunpark.android.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface WriteApi {

    @Multipart
    @POST("user/article")
    suspend fun write(
        @Header("Authorization") accessToken: String,
        @Part("data") data: RequestBody,
        @Part file: List<MultipartBody.Part>
    ): Response<Unit>
}