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
    private val twitterApi: TwitterApi,
    private val homeTimelineApiToModel: HomeTimelineApiToModel
) : RequestTwitterApiRepository {

    override suspend fun getToken(): String {
        return requestTokenApi.httpRequestToken()
    }

    override suspend fun accessToken(verifier: String): Map<String, String> {

        val accessToken: Map<String, String> = oauthConsumerMapper.invoke(
            requestTokenApi.getAccessToken(verifier)
        )

        accessToken["oauthToken"]?.let { preferences.saveOAuthToken(it) }
        accessToken["oauthTokenSecret"]?.let { preferences.saveOAuthTokenSecret(it) }

        return accessToken
    }

    override suspend fun getHomeTimeline(): Reslt<HomeTimelineModel> {


        return try {
            val response = twitterApi.getHomeTimeline()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Reslt.Success(homeTimelineApiToModel.mapHomeTimelineApiToModel(it))
                } ?: Reslt.Failure(null, "An unknown error occured")
            } else {
                Log.i("mLog", "response error = ${response.errorBody()?.string()}")
                Reslt.Failure(null, "An unknown error occured")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Reslt.Failure(e, "Couldn't reach the server. Check you internet connection")
        }
    }
}