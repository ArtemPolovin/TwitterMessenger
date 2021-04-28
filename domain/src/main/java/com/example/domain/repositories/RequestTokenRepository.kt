package com.example.domain.repositories

interface RequestTokenRepository {
   suspend fun getToken(): String
   suspend fun accessToken(verifier: String): Map<String, String>
}