package com.seojunpark.android.domain.repository

import com.seojunpark.android.data.dto.LoginDTO
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
    ): Flow<LoginDTO>
}