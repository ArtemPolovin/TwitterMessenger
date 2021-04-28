package com.example.data.mapers

import oauth.signpost.OAuthConsumer

class OAuthConsumerToMap {
    operator fun invoke(consumer: OAuthConsumer): Map<String, String> {
         val oauthToken = consumer.token
        val oauthTokenSecret = consumer.tokenSecret

        return mapOf("oauthToken" to oauthToken, "oauthTokenSecret" to oauthTokenSecret)
    }
}