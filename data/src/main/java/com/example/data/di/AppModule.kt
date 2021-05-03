package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.common.Constants.SHARED_PREFS
import com.example.data.common.Preferences
import com.example.data.mapers.HomeTimelineApiToModel
import com.example.data.mapers.OAuthConsumerToMap
import com.example.data.network.RequestTokenApi
import com.example.data.network.TwitterApi
import com.example.data.repositoryimpl.RequestTwitterApiRepositoryImpl
import com.example.domain.repositories.RequestTwitterApiRepository
import com.example.domain.usecases.GetAccessTokenUseCase
import com.example.domain.usecases.GetHomeTimelineUseCase
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
        requestTokenApi: RequestTokenApi,
        preferences: Preferences,
        oauthConsumerToMap: OAuthConsumerToMap,
        twitterApi: TwitterApi,
        homeTimelineApiToModel: HomeTimelineApiToModel
    ): RequestTwitterApiRepository =
        RequestTwitterApiRepositoryImpl(
            requestTokenApi,
            preferences,
            oauthConsumerToMap,
            twitterApi,
            homeTimelineApiToModel
        )


    @Provides
    fun provideGetTokenUseCase(requestTwitterApiRepo: RequestTwitterApiRepository) =
        GetTokenUseCase(requestTwitterApiRepo)

    @Provides
    fun provideGetAccessTokenUseCase(requestTwitterApiRepo: RequestTwitterApiRepository) =
        GetAccessTokenUseCase(requestTwitterApiRepo)

    @Provides
    fun provideGetHomeTimelineUseCase(requestTwitterApiRepo: RequestTwitterApiRepository) =
        GetHomeTimelineUseCase(requestTwitterApiRepo)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPref: SharedPreferences) = Preferences(sharedPref)

    @Provides
    @Singleton
    fun provideRequestTokenApi() = RequestTokenApi()

    @Provides
    @Singleton
    fun provideTwitterApi(preferences: Preferences): TwitterApi{
        val accessToken = preferences.loadOAuthToken()
        val accessTokenSecret = preferences.loadOAuthTokenSecret()
        return TwitterApi.invoke(accessToken,accessTokenSecret)
    }

    @Provides
    fun provideHomeTimelineApiToModelMapper() = HomeTimelineApiToModel()

    @Provides
    fun provideOAthConsumerToMap() = OAuthConsumerToMap()


}