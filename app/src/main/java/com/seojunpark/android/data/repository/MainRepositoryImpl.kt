package com.seojunpark.android.data.repository

import android.util.Log
import com.seojunpark.android.data.dto.response.DetailResponse
import com.seojunpark.android.data.dto.response.LoginResponse
import com.seojunpark.android.data.dto.request.LoginRequest
import com.seojunpark.android.data.dto.response.MainResponse
import com.seojunpark.android.data.dto.request.SignUpRequest
import com.seojunpark.android.data.dto.response.ProfileResponse
import com.seojunpark.android.data.dto.response.RequestResponse
import com.seojunpark.android.data.dto.response.WriteListResponse
import com.seojunpark.android.data.remote.DetailApi
import com.seojunpark.android.data.remote.LoginApi
import com.seojunpark.android.data.remote.MainApi
import com.seojunpark.android.data.remote.ProfileApi
import com.seojunpark.android.data.remote.RequestApi
import com.seojunpark.android.data.remote.SignUpApi
import com.seojunpark.android.data.remote.WriteApi
import com.seojunpark.android.data.remote.WriteListApi
import com.seojunpark.android.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val signUpApi: SignUpApi,
    private val loginApi: LoginApi,
    private val mainApi: MainApi,
    private val detailApi: DetailApi,
    private val writeApi: WriteApi,
    private val profileApi: ProfileApi,
    private val writeListApi: WriteListApi,
    private val requestApi: RequestApi
) : MainRepository {

    override suspend fun signUp(
        email: String,
        name: String,
        password: String,
        rePassword: String
    ): Pair<Boolean, String> {
        return try {
            val response = signUpApi.signUp(SignUpRequest(email, name, password, rePassword))
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

    override suspend fun request(accessToken: String, id: Long): Pair<Boolean, String> {
        return try {
            val response = detailApi.request(accessToken, id)
            when (response.code()) {
                200 -> {
                    Pair(true, "성공적으로 신청되었습니다.")
                }

                401 -> {
                    Pair(false, "유효한 토큰이 아닙니다.")
                }

                403 -> {
                    Pair(false, "본인 게시글입니다.")
                }

                404 -> {
                    Pair(false, "게시글이 없습니다..")
                }

                409 -> {
                    Pair(false, "이미 신청한 유저가 있습니다.")
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

    override suspend fun write(
        accessToken: String,
        data: RequestBody,
        files: List<MultipartBody.Part>
    ): Pair<Boolean, String> {
        return try {
            val response = writeApi.write(accessToken, data, files)
            when (response.code()) {
                200 -> {
                    Pair(true, "성공적으로 신청되었습니다.")
                }

                401 -> {
                    Pair(false, "유효한 토큰이 아닙니다.")
                }

                403 -> {
                    Pair(false, "본인 게시글입니다.")
                }

                404 -> {
                    Pair(false, "게시글이 없습니다..")
                }

                409 -> {
                    Pair(false, "이미 신청한 유저가 있습니다.")
                }

                else -> {
                    Log.d("response", response.code().toString())
                    Pair(false, "잘못된 요청")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, "실패")
        }
    }


    override fun userInfo(accessToken: String): Flow<ProfileResponse> {
        return flow {
            try {
                val response = profileApi.userInfo(accessToken)
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

    override fun writeList(accessToken: String): Flow<WriteListResponse> {
        return flow {
            try {
                val response = writeListApi.writeList(accessToken)
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

    override fun requestList(accessToken: String): Flow<RequestResponse> {
        return flow {
            try {
                val response = requestApi.requestList(accessToken)
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

    override suspend fun check(accessToken: String, id: Long): Pair<Boolean, String> {
        return try {
            val response = detailApi.check(accessToken, id)
            when (response.code()) {
                200 -> {
                    Pair(true, "성공적으로 신청되었습니다.")
                }

                401 -> {
                    Pair(false, "유효한 토큰이 아닙니다.")
                }

                403 -> {
                    Pair(false, "게시글 작성자가 요청하지 않았습니다.")
                }

                else -> {
                    Log.d("response", response.code().toString())
                    Pair(false, "잘못된 요청")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, "실패")
        }
    }
}