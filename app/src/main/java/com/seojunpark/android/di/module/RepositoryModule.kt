package com.seojunpark.android.di.module

import com.seojunpark.android.data.remote.DetailApi
import com.seojunpark.android.data.remote.LoginApi
import com.seojunpark.android.data.remote.MainApi
import com.seojunpark.android.data.remote.ProfileApi
import com.seojunpark.android.data.remote.RequestApi
import com.seojunpark.android.data.remote.SignUpApi
import com.seojunpark.android.data.remote.WriteApi
import com.seojunpark.android.data.remote.WriteListApi
import com.seojunpark.android.data.repository.MainRepositoryImpl
import com.seojunpark.android.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesContentRepository(
        signUpApi: SignUpApi,
        loginApi: LoginApi,
        mainApi: MainApi,
        detailApi: DetailApi,
        writeApi: WriteApi,
        profileApi: ProfileApi,
        writeListApi: WriteListApi,
        requestApi: RequestApi
    ): MainRepository = MainRepositoryImpl(signUpApi, loginApi, mainApi, detailApi, writeApi, profileApi, writeListApi, requestApi)
}