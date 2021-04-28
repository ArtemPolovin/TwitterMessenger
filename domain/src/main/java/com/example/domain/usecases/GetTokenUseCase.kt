package com.example.domain.usecases

import com.example.domain.repositories.RequestTokenRepository

class GetTokenUseCase(private val requestTokenRepository: RequestTokenRepository) {
    suspend operator fun invoke() = requestTokenRepository.getToken()
}