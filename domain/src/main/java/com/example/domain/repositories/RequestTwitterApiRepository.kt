package com.example.domain.repositories

import com.example.domain.common.Reslt
import com.example.domain.models.HomeTimelineModel

interface RequestTwitterApiRepository {
   suspend fun getToken(): String
   suspend fun getAccessToken(verifier: String)
   suspend fun getHomeTimeline(): Reslt<List<HomeTimelineModel>>

}