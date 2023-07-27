package com.seojunpark.android.di.module

import com.seojunpark.android.data.remote.DetailApi
import com.seojunpark.android.data.remote.LoginApi
import com.seojunpark.android.data.remote.MainApi
import com.seojunpark.android.data.remote.SignUpApi
import com.seojunpark.android.data.remote.WriteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun providesSignUpService(retrofit: Retrofit): SignUpApi = retrofit.create(SignUpApi::class.java)

    @Singleton
    @Provides
    fun providesLoginService(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

    @Singleton
    @Provides
    fun providesMainService(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)

    @Singleton
    @Provides
    fun providesDetailService(retrofit: Retrofit): DetailApi = retrofit.create(DetailApi::class.java)

    @Singleton
    @Provides
    fun providesWriteService(retrofit: Retrofit): WriteApi = retrofit.create(WriteApi::class.java)
}