package com.seojunpark.android.domain.repository

import com.seojunpark.android.data.dto.response.DetailResponse
import com.seojunpark.android.data.dto.response.LoginResponse
import com.seojunpark.android.data.dto.response.MainResponse
import com.seojunpark.android.data.dto.response.ProfileResponse
import com.seojunpark.android.data.dto.response.RequestResponse
import com.seojunpark.android.data.dto.response.WriteListResponse
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

    suspend fun request(
        accessToken: String,
        id: Long
    ): Pair<Boolean, String>

    suspend fun write(
        accessToken: String,
        data: RequestBody,
        file: List<MultipartBody.Part>
    ): Pair<Boolean, String>

    fun userInfo(
        accessToken: String
    ): Flow<ProfileResponse>

    fun writeList(
        accessToken: String
    ): Flow<WriteListResponse>

    fun requestList(
        accessToken: String
    ): Flow<RequestResponse>

    suspend fun check(
        accessToken: String,
        id: Long
    ): Pair<Boolean, String>
}