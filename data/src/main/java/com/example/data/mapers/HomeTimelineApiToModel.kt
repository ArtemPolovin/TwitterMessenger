package com.example.data.mapers

import com.example.data.modelsapi.hometimeline.HomeTimelineApiModel
import com.example.domain.models.HomeTimelineModel

class HomeTimelineApiToModel {
    fun map(homeTimeline: HomeTimelineApiModel): HomeTimelineModel {
        return HomeTimelineModel(
            name = homeTimeline[0].user.name,
            tweet = homeTimeline[0].text
        )
    }
}