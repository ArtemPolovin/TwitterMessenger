package com.example.data.mapers

import com.example.data.common.getPublishedTwitTime
import com.example.data.modelsapi.hometimeline.ExtendedEntities
import com.example.data.modelsapi.hometimeline.HomeTimelineApiModel
import com.example.domain.models.HomeTimelineModel

class HomeTimelineApiToModel {

    fun map(homeTimeline: HomeTimelineApiModel): List<HomeTimelineModel> {

        return homeTimeline.map {
            HomeTimelineModel(
                name = it.user.name,
                tweet = it.text.substringBefore("https://t.co/"), // the url is attached to the end of text if tweet goes with images
                nickname = it.user.screen_name,
                publishedTwitDate = getPublishedTwitTime(it.created_at),
                profileImageURL = it.user.profile_image_url_https,
                attachedImages = setupAttachedPhotos(it.extended_entities)
            )
        }
    }

    private fun setupAttachedPhotos(extendedEntities: ExtendedEntities?): List<String>? {
        return extendedEntities?.media?.map{ it.media_url_https }
    }

}