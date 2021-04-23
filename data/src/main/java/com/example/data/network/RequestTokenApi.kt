package com.example.data.network

import android.util.Log
import com.example.data.common.Constants.CONSUMER_KEY
import com.example.data.common.Constants.CONSUMER_SECRET
import com.example.data.common.Constants.URL_ACCESS_TOKEN
import com.example.data.common.Constants.URL_AUTHORIZE
import com.example.data.common.Constants.URL_REQUEST_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import oauth.signpost.OAuth
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider

class RequestTokenApi {

    suspend fun httpRequestToken(): String {

        val consumer: OAuthConsumer = CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
        val provider: OAuthProvider = CommonsHttpOAuthProvider(
            URL_REQUEST_TOKEN,
            URL_ACCESS_TOKEN,
            URL_AUTHORIZE
        )

        withContext(Dispatchers.IO) {
            provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND)
        }

        return consumer.token
    }

}