package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DetailApi {

    @GET("user/article/detail/{id}")
    suspend fun loadDetailList(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long
    ): Response<DetailResponse>
}