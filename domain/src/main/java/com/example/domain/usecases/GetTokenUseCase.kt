package com.example.domain.usecases

import com.example.domain.repositories.RequestTwitterApiRepository

class GetTokenUseCase(private val requestTwitterApiRepository: RequestTwitterApiRepository) {
    suspend operator fun invoke() = requestTwitterApiRepository.getToken()
}