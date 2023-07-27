package com.seojunpark.android.domain.repository

import com.seojunpark.android.data.dto.LoginResponse
import com.seojunpark.android.data.dto.MainResponse
import kotlinx.coroutines.flow.Flow

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
}