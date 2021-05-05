package com.example.data.network

import android.util.Log
import com.example.data.common.Constants.CONSUMER_KEY
import com.example.data.common.Constants.CONSUMER_SECRET
import com.example.data.common.Constants.TWITTER_BASE_URL
import com.example.data.modelsapi.hometimeline.HomeTimelineApiModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

interface TwitterApi {

    @GET("/1.1/statuses/home_timeline.json")
    suspend fun getHomeTimeline(): Response<HomeTimelineApiModel>

    companion object{
        operator fun invoke(accessToken: String, accessTokenSecret: String): TwitterApi{

            val consumer = OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
            consumer.setTokenWithSecret(accessToken,accessTokenSecret)

            val client = OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(consumer))
                .build()

            return Retrofit.Builder()
                .baseUrl(TWITTER_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TwitterApi::class.java)
        }
    }

}