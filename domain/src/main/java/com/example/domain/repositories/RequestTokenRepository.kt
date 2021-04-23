package com.example.domain.repositories

import kotlinx.coroutines.Deferred

interface RequestTokenRepository {
   suspend fun getToken(): String
}