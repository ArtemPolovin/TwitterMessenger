package com.example.data.repositoryimpl

import android.util.Log
import com.example.data.common.Preferences
import com.example.data.mapers.HomeTimelineApiToModel
import com.example.data.mapers.OAuthConsumerToMap
import com.example.data.network.RequestTokenApi
import com.example.data.network.TwitterApi
import com.example.domain.common.Reslt
import com.example.domain.models.HomeTimelineModel
import com.example.domain.repositories.RequestTwitterApiRepository

class RequestTwitterApiRepositoryImpl(
    private val requestTokenApi: RequestTokenApi,
    private val preferences: Preferences,
    private val oauthConsumerMapper: OAuthConsumerToMap,
    private val twitterApi: dagger.Lazy<TwitterApi>,
    private val homeTimelineApiToModel: HomeTimelineApiToModel
) : RequestTwitterApiRepository {

    override suspend fun getToken(): String {
        return requestTokenApi.httpRequestToken()
    }

    override suspend fun getAccessToken(verifier: String) {

        try {
            val consumer = requestTokenApi.getAccessToken(verifier)
            if (!(consumer.token.isNullOrEmpty() && consumer.tokenSecret.isNullOrEmpty())) {
                val accessToken = oauthConsumerMapper.invoke(consumer)
                saveAccessToken(accessToken)
                Reslt.Success(accessToken)
            } else {
                Reslt.Failure(message = "The access token was not got")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Reslt.Failure(message = "Couldn't reach the server. Check you internet connection")
        }

    }

    override suspend fun getHomeTimeline(): Reslt<HomeTimelineModel> {

        return try {

            val response = twitterApi.get().getHomeTimeline()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Reslt.Success(homeTimelineApiToModel.map(it))
                } ?: Reslt.Failure(message = "An unknown error occured")
            } else {
                Log.i("mLog", "response error = ${response.errorBody()?.string()}")
                Reslt.Failure(message = "An unknown error occured")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Reslt.Failure(e, "Couldn't reach the server. Check you internet connection")
        }
    }

    private fun saveAccessToken(tokenMap: Map<String, String>) {
        tokenMap["oauthToken"]?.let { preferences.saveOAuthToken(it) }
        tokenMap["oauthTokenSecret"]?.let { preferences.saveOAuthTokenSecret(it) }
    }
}