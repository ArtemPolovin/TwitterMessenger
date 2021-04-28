package com.example.twittermessenger.ui.authorization

import android.net.Uri
import android.webkit.WebResourceRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.common.Constants
import com.example.domain.usecases.GetAccessTokenUseCase
import com.example.domain.usecases.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URISyntaxException
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

    fun handleUrl(request: WebResourceRequest?): Boolean {
        var uri: Uri? = null

        request?.let { it ->
            if (it.url.toString().contains(Constants.CALL_BACK_URL)) {

                try {
                    uri = Uri.parse(it.url.toString())
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                }

                uri?.let {
                    val verifier = it.getQueryParameter(com.example.twittermessenger.common.Constants.OAUTH_VERIFIER_PARAMETER_KEY)
                    verifier?.let { myAccessToken(verifier = verifier) }
                }
                return true
            }
        }

        return false
    }

    fun receiveToken() {
        fetchToken()
    }

}