package com.example.twittermessenger.ui.authorization

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.twittermessenger.R
import com.example.twittermessenger.common.Constants.TWITTER_AUTHORIZATION_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_autorization.*

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_autorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)

        authorizationChecking()
    }

    private fun authorizationChecking() {
        if (viewModel.isAuthorized()) {
            navController.navigate(R.id.action_authorizationFragment_to_tweetFragment)
        } else {
            viewModel.receiveToken()
            webViewSetup()
        }

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

}