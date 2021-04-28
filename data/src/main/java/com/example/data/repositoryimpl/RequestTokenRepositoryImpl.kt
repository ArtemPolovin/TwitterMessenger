package com.example.data.repositoryimpl

import com.example.data.common.Preferences
import com.example.data.mapers.OAuthConsumerToMap
import com.example.data.network.RequestTokenApi
import com.example.domain.repositories.RequestTokenRepository

class RequestTokenRepositoryImpl(
    private val requestTokenApi: RequestTokenApi,
    private val preferences: Preferences,
    private val oauthConsumerMapper: OAuthConsumerToMap
) : RequestTokenRepository {

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
}