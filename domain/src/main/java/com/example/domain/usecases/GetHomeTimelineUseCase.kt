package com.example.domain.usecases

import com.example.domain.repositories.RequestTwitterApiRepository

class GetHomeTimelineUseCase(private val requestTwitterApiRepository: RequestTwitterApiRepository) {
    suspend operator fun invoke() = requestTwitterApiRepository.getHomeTimeline()
}