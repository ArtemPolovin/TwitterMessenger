package com.example.data.repositoryimpl

import com.example.data.network.RequestTokenApi
import com.example.domain.repositories.IRequestTokenRepository

class RequestTokenRepositoryImpl(
    private val api: RequestTokenApi
) : IRequestTokenRepository {

    override suspend fun getToken(): String {
       return api.httpRequestToken().await()
    }

}