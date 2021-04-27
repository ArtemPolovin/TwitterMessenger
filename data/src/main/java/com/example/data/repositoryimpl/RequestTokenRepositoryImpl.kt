package com.example.data.repositoryimpl

import com.example.data.network.RequestTokenApi
import com.example.domain.repositories.RequestTokenRepository

class RequestTokenRepositoryImpl(
    private val requestTokenApi: RequestTokenApi
) : RequestTokenRepository {

    override suspend fun getToken(): String {
        return requestTokenApi.httpRequestToken()
    }

    override suspend fun accessToken(verifier: String): Map<String, String> {
       return requestTokenApi.getAccessToken(verifier)
    }

}