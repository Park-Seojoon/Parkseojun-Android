package com.seojunpark.android.domain.usecase

import com.seojunpark.android.domain.repository.MainRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun signUp(
        email: String,
        name: String,
        password: String,
        rePassword: String
    ) = mainRepository.signUp(
        email,
        name,
        password,
        rePassword
    )

    fun login(
        email: String,
        password: String
    ) = mainRepository.login(
        email,
        password
    )

    fun loadList(accessToken: String) = mainRepository.loadList(accessToken)

    fun loadDetailList(accessToken: String, id: Long) = mainRepository.loadDetailList(accessToken, id)
}