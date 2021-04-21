package com.example.twittermessenger.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
): ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    private fun fetchToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase()
        }
    }

    fun receiveToken() {
        fetchToken()
    }

}