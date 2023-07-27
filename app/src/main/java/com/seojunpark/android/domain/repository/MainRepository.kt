package com.seojunpark.android.domain.repository

import com.seojunpark.android.data.dto.response.DetailResponse
import com.seojunpark.android.data.dto.response.LoginResponse
import com.seojunpark.android.data.dto.response.MainResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MainRepository {

    suspend fun signUp(
        email: String,
        name: String,
        password: String,
        rePassword: String
    ): Pair<Boolean, String>

    fun login(
        email: String,
        password: String
    ): Flow<LoginResponse>

    fun loadList(
        accessToken: String
    ): Flow<MainResponse>

    fun loadDetailList(
        accessToken: String,
        id: Long
    ): Flow<DetailResponse>

    fun request(
        accessToken: String,
        id: Long
    ): Flow<Unit>

    fun write(
        accessToken: String,
        data: RequestBody,
        files: List<MultipartBody.Part>
    ): Flow<Unit>
}