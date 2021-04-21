package com.example.domain.usecases

import com.example.domain.repositories.IRequestTokenRepository

class GetTokenUseCase(private val requestTokenRepository: IRequestTokenRepository) {
    suspend operator fun invoke() = requestTokenRepository.getToken()
}