package com.seojunpark.android.data.repository

import android.util.Log
import com.seojunpark.android.data.dto.DetailResponse
import com.seojunpark.android.data.dto.LoginResponse
import com.seojunpark.android.data.dto.LoginRequest
import com.seojunpark.android.data.dto.MainResponse
import com.seojunpark.android.data.dto.SignUpRequest
import com.seojunpark.android.data.remote.DetailApi
import com.seojunpark.android.data.remote.LoginApi
import com.seojunpark.android.data.remote.MainApi
import com.seojunpark.android.data.remote.SignUpApi
import com.seojunpark.android.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val signUpApi: SignUpApi,
    private val loginApi: LoginApi,
    private val mainApi: MainApi,
    private val detailApi: DetailApi
) : MainRepository {

    override suspend fun signUp(
        email: String,
        name: String,
        password: String,
        rePassword: String
    ): Pair<Boolean, String> {
        return try {
            val response = signUpApi.signUp(SignUpRequest( email, name, password, rePassword))
            when (response.code()) {
                201 -> {
                    Pair(true, "회원가입이 완료되었습니다.")
                }

                400 -> {
                    Pair(false, "요청 형식이 올바르지 않습니다.")
                }

                409 -> {
                    Pair(false, "중복된 이메일입니다.")
                }

                else -> {
                    Log.d("response", response.code().toString())
                    Pair(false, "잘못된 요청")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Pair(false, "실패")
        }
    }

    override fun login(email: String, password: String): Flow<LoginResponse> {
        return flow {
            try {
                val response = loginApi.login(LoginRequest(email, password))
                when (response.code()) {
                    200 -> {
                        val result = response.body()
                        if (result != null) {
                            emit(result)
                        }
                    }

                    400 -> {

                    }

                    404 -> {

                    }

                    else -> {

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadList(accessToken: String): Flow<MainResponse> {
        return flow {
            try {
                val response = mainApi.loadList(accessToken)
                if (response.isSuccessful) {
                    val list = response.body()
                    if (list != null) {
                        emit(list)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadDetailList(accessToken: String, id: Long): Flow<DetailResponse> {
        return flow {
            try {
                val response = detailApi.loadDetailList(accessToken, id)
                if (response.isSuccessful) {
                    val list = response.body()
                    if (list != null) {
                        emit(list)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun request(accessToken: String, id: Long): Flow<Unit> {
        return flow {
            try {
                val response = detailApi.request(accessToken, id)
                if (response.isSuccessful) {
                    val result = response.code()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}