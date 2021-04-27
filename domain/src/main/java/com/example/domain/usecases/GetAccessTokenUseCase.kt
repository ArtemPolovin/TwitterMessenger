package com.example.domain.usecases

import com.example.domain.repositories.RequestTokenRepository

class GetAccessTokenUseCase(private val requestTokenRepo: RequestTokenRepository) {

   suspend operator fun invoke(verifier: String) =
        requestTokenRepo.accessToken(verifier)

}