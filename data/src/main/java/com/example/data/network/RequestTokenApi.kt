package com.example.data.network

import android.util.Log
import com.example.data.common.Constants.CALL_BACK_URL
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
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RequestTokenApi {

    private val consumer: OAuthConsumer = CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
    private val provider: OAuthProvider = CommonsHttpOAuthProvider(
        URL_REQUEST_TOKEN,
        URL_ACCESS_TOKEN,
        URL_AUTHORIZE
    )

    suspend fun httpRequestToken(): String {


        withContext(Dispatchers.IO) {
            provider.retrieveRequestToken(consumer, CALL_BACK_URL)
        }

        return consumer.token
    }

    suspend fun getAccessToken(verifier: String): OAuthConsumer {

        withContext(Dispatchers.IO) {
            provider.retrieveAccessToken(consumer, verifier)
        }

        return consumer
    }

}