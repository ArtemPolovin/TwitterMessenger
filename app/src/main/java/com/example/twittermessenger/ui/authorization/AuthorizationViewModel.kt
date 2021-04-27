package com.example.twittermessenger.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetAccessTokenUseCase
import com.example.domain.usecases.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    private val _accessToken = MutableLiveData<Map<String, String>>()
    val accessToken: LiveData<Map<String,String>> get() = _accessToken


    private fun fetchToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase()
        }
    }

    private fun myAccessToken(verifier: String) {
        viewModelScope.launch {
           _accessToken.value =  getAccessTokenUseCase.invoke(verifier)
        }
    }

    fun receiveToken() {
        fetchToken()
    }

    fun receiveAccessToken(verifier: String) {
        myAccessToken(verifier)
    }

}