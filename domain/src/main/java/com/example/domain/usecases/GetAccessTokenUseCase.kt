package com.example.domain.usecases

import com.example.domain.repositories.RequestTwitterApiRepository

class GetAccessTokenUseCase(private val requestTwitterApiRepo: RequestTwitterApiRepository) {

   suspend operator fun invoke(verifier: String) =
        requestTwitterApiRepo.accessToken(verifier)

}