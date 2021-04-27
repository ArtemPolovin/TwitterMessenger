package com.example.data.network

import com.example.data.common.AccessTokenCache
import com.example.data.common.Constants.CONSUMER_KEY
import com.example.data.common.Constants.CONSUMER_SECRET
import com.example.data.common.Constants.URL_ACCESS_TOKEN
import com.example.data.common.Constants.URL_AUTHORIZE
import com.example.data.common.Constants.URL_REQUEST_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider

class RequestTokenApi(private val accessTokenCache: AccessTokenCache) {

    private val consumer: OAuthConsumer = CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
    private val provider: OAuthProvider = CommonsHttpOAuthProvider(
        URL_REQUEST_TOKEN,
        URL_ACCESS_TOKEN,
        URL_AUTHORIZE
    )

    suspend fun httpRequestToken(): String {


        withContext(Dispatchers.IO) {
            provider.retrieveRequestToken(consumer, "https://www.youtube.com")
        }

        return consumer.token
    }

    suspend fun getAccessToken(verifier: String): Map<String, String> {

        withContext(Dispatchers.IO) {
            provider.retrieveAccessToken(consumer, verifier)
        }

        val oauthToken = consumer.token
        val oauthTokenSecret = consumer.tokenSecret

        accessTokenCache.saveOAuthToken(oauthToken)
        accessTokenCache.saveOAuthTokenSecret(oauthTokenSecret)
        return mapOf("oauthToken" to oauthToken, "oauthTokenSecret" to oauthTokenSecret)
    }

}