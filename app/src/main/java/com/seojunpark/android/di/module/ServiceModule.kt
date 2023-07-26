package com.seojunpark.android.di.module

import com.seojunpark.android.data.remote.LoginApi
import com.seojunpark.android.data.remote.SignUpApi
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
}