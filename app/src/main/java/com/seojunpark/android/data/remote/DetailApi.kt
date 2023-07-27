package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.response.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DetailApi {

    @GET("user/article/detail/{id}")
    suspend fun loadDetailList(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long
    ): Response<DetailResponse>

    @POST("user/article/doit/{id}")
    suspend fun request(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long
    ): Response<Unit>
}