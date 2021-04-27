package com.example.twittermessenger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.twittermessenger.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment


        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

/*    override fun onResume() {
        super.onResume()
        val url = intent.data
        if (url != null) {
            Log.i(">>>>>>>>>>>>>>>>>>", "$url")
            Toast.makeText(this, "yay!!!", Toast.LENGTH_LONG).show()
        } else {
            Log.i("..................","url = null")
        }
    }*/
}