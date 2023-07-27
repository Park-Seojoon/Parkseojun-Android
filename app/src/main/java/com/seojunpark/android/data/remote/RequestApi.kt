package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.response.RequestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RequestApi {

    @GET("user/article/doMyList")
    suspend fun requestList(
        @Header("Authorization") accessToken: String
    ): Response<RequestResponse>
}