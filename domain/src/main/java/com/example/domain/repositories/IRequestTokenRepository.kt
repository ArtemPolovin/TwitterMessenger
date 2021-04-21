package com.example.domain.repositories

import kotlinx.coroutines.Deferred

interface IRequestTokenRepository {
   suspend fun getToken(): String
}