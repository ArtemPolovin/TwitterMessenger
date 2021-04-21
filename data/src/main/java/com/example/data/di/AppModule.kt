package com.example.data.di

import com.example.data.network.RequestTokenApi
import com.example.data.repositoryimpl.RequestTokenRepositoryImpl
import com.example.domain.repositories.IRequestTokenRepository
import com.example.domain.usecases.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRequestTokenApi() = RequestTokenApi()

    @Provides
    @Singleton
    fun provideRequestTokenRepositoryImpl(requestTokenApi: RequestTokenApi): IRequestTokenRepository =
        RequestTokenRepositoryImpl(requestTokenApi)

    @Provides
    fun provideGetTokenUseCase(requestTokenRepo: IRequestTokenRepository) = GetTokenUseCase(requestTokenRepo)

}