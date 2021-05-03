package com.example.data.repositoryimpl

import android.util.Log
import com.example.data.common.Preferences
import com.example.data.mapers.HomeTimelineApiToModel
import com.example.data.mapers.OAuthConsumerToMap
import com.example.data.network.RequestTokenApi
import com.example.data.network.TwitterApi
import com.example.domain.common.Resource
import com.example.domain.models.HomeTimelineModel
import com.example.domain.repositories.RequestTwitterApiRepository
import java.lang.Exception

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

    override suspend fun getHomeTimeline(): Resource<HomeTimelineModel> {

       return try {
            val response = twitterApi.getHomeTimeline()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(homeTimelineApiToModel.mapHomeTimelineApiToModel(it))
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Log.i("mLog","response error = ${response.errorBody()?.string()}")
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error("Couldn't reach the server. Check you internet connection",null)
        }
    }
}