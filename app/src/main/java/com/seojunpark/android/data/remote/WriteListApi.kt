package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.response.WriteListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface WriteListApi {

    @GET("user/article/myList")
    suspend fun writeList(
        @Header("Authorization") accessToken: String
    ): Response<WriteListResponse>
}