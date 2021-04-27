package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.common.AccessTokenCache
import com.example.data.common.Constants.SHARED_PREFS
import com.example.data.network.RequestTokenApi
import com.example.data.repositoryimpl.RequestTokenRepositoryImpl
import com.example.domain.repositories.RequestTokenRepository
import com.example.domain.usecases.GetAccessTokenUseCase
import com.example.domain.usecases.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRequestTokenRepositoryImpl(
        requestTokenApi: RequestTokenApi
    ): RequestTokenRepository =
        RequestTokenRepositoryImpl(requestTokenApi)


    @Provides
    fun provideGetTokenUseCase(requestTokenRepo: RequestTokenRepository) =
        GetTokenUseCase(requestTokenRepo)

    @Provides
    fun provideGetAccessTokenUseCase(requestTokenRepo: RequestTokenRepository) =
        GetAccessTokenUseCase(requestTokenRepo)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAccessTokenCache(sharedPref: SharedPreferences) = AccessTokenCache(sharedPref)

    @Provides
    @Singleton
    fun provideRequestTokenApi(accessTokenCache: AccessTokenCache) = RequestTokenApi(accessTokenCache)


}