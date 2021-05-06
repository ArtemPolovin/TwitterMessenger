package com.example.twittermessenger.ui.authorization

import android.net.Uri
import android.webkit.WebResourceRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.common.Constants
import com.example.data.common.Preferences
import com.example.domain.usecases.GetAccessTokenUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.twittermessenger.common.Constants.OAUTH_VERIFIER_PARAMETER_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _receivedAccessToken = Channel<Boolean>(Channel.BUFFERED)
    val receivedAccessToken = _receivedAccessToken.receiveAsFlow()

    private fun fetchToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase.invoke()
        }
    }

    private fun fetchAccessToken(verifier: String) {
        viewModelScope.launch {
            getAccessTokenUseCase.invoke(verifier)
            _receivedAccessToken.send(isAuthorized())
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