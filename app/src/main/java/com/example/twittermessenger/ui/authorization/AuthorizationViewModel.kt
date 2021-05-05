package com.example.twittermessenger.ui.authorization

import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.common.Constants
import com.example.data.common.Preferences
import com.example.domain.common.Reslt
import com.example.domain.usecases.GetAccessTokenUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.twittermessenger.common.Constants.OAUTH_VERIFIER_PARAMETER_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URISyntaxException
import javax.inject.Inject


@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    private fun fetchToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase.invoke()
        }
    }

    private fun fetchAccessToken(verifier: String) {
        viewModelScope.launch {
            _accessToken.value =  getAccessTokenUseCase.invoke(verifier)
        }
    }

    private val _accessToken = MutableLiveData<Reslt<Map<String,String>>>()
    val accessToken: LiveData<Reslt<Map<String,String>>>get() = _accessToken


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
                    val verifier =
                        it.getQueryParameter(OAUTH_VERIFIER_PARAMETER_KEY)
                    verifier?.let { fetchAccessToken(verifier = verifier) }
                }
                return true
            }
        }
        return false
    }

    fun receiveToken() {
        fetchToken()
    }

    fun isAuthorized(): Boolean {
        val authToken = preferences.loadOAuthToken()
        val authTokenSecret = preferences.loadOAuthTokenSecret()

        return authToken.isNotEmpty() && authTokenSecret.isNotEmpty()

    }


}