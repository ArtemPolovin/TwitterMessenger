package com.example.data.common

import android.content.SharedPreferences
import com.example.data.common.Constants.ACCESS_OAUTH_TOKEN
import com.example.data.common.Constants.ACCESS_OAUTH_TOKEN_SECRET

 class Preferences(
    private val sharedPref: SharedPreferences
){

    fun saveOAuthToken(oauthToken: String) {
        sharedPref.edit().putString(ACCESS_OAUTH_TOKEN,oauthToken).commit()
    }

    fun saveOAuthTokenSecret(oauthTokenSecret: String) {
        sharedPref.edit().putString(ACCESS_OAUTH_TOKEN_SECRET,oauthTokenSecret).commit()
    }

    fun loadOAuthToken(): String {
        return sharedPref.getString(ACCESS_OAUTH_TOKEN, "") ?: ""
    }

    fun loadOAuthTokenSecret(): String {
        return sharedPref.getString(ACCESS_OAUTH_TOKEN_SECRET,"")?: ""
    }
}

