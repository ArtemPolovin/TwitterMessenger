package com.example.data.network

import com.example.data.common.Constants.CONSUMER_KEY
import com.example.data.common.Constants.CONSUMER_SECRET
import com.example.data.common.Constants.URL_ACCESS_TOKEN
import com.example.data.common.Constants.URL_AUTHORIZE
import com.example.data.common.Constants.URL_REQUEST_TOKEN
import kotlinx.coroutines.*
import oauth.signpost.OAuth
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider

class RequestTokenApi {

    fun  httpRequestToken()= GlobalScope.async{


            val consumer: OAuthConsumer = CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
            val provider: OAuthProvider = CommonsHttpOAuthProvider(
                URL_REQUEST_TOKEN,
                URL_ACCESS_TOKEN,
                URL_AUTHORIZE
            )

                provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND)
                consumer.token

    }

}