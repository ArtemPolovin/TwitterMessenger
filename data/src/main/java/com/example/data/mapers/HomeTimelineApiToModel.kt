package com.example.data.mapers

import com.example.data.modelsapi.HomeTimelineApiModel
import com.example.domain.models.HomeTimelineModel

class HomeTimelineApiToModel {
    fun mapHomeTimelineApiToModel(homeTimeline: HomeTimelineApiModel): HomeTimelineModel {
        return HomeTimelineModel(
            name = homeTimeline[0].user.name,
            tweet = homeTimeline[0].text
        )
    }
}