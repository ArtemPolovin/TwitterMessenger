package com.example.twittermessenger.ui.authorization

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.twittermessenger.R
import com.example.twittermessenger.common.Constants.TWITTER_AUTHORIZATION_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_autorization.*

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_autorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.receiveToken()
        webViewSetup()
        accessTokenSetup()

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {

        viewModel.token.observe(viewLifecycleOwner, Observer { token ->

            web_view.apply {
                loadUrl("$TWITTER_AUTHORIZATION_URL$token")
                settings.javaScriptEnabled = true
            }

            web_view.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {

                   return viewModel.handleUrl(request)
                }
            }


        })

    }

    private fun accessTokenSetup() {
        viewModel.accessToken.observe(viewLifecycleOwner, Observer {
            Log.i(
                "AAAAAAAAAAAAAAA",
                "oauthToken = ${it["oauthToken"]}\n oauthTokenSecret = ${it["oauthTokenSecret"]}"
            )
        })
    }
}