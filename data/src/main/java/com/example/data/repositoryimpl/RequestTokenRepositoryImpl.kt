package com.example.data.repositoryimpl

import com.example.data.network.RequestTokenApi
import com.example.domain.repositories.RequestTokenRepository

class RequestTokenRepositoryImpl(
    private val api: RequestTokenApi
) : RequestTokenRepository {

    override suspend fun getToken(): String {
       return api.httpRequestToken()
    }

}