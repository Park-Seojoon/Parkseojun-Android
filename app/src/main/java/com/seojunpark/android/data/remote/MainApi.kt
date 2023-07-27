package com.seojunpark.android.data.remote

import com.seojunpark.android.data.dto.response.MainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApi {

    @GET("user/article/list")
    suspend fun loadList(
        @Header("Authorization") accessToken: String
    ): Response<MainResponse>
}