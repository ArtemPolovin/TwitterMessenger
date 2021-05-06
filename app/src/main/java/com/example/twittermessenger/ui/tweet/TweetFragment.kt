package com.example.twittermessenger.ui.tweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.domain.common.Reslt
import com.example.twittermessenger.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tweet.*

@AndroidEntryPoint
class TweetFragment : Fragment() {

    private val viewModel: TweetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.refreshHomeTimelineData()

        setUpHomeTimeline()
    }

    private fun setUpHomeTimeline() {
        viewModel.homeTimeline.observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = View.GONE
            text_error.visibility = View.GONE

            when (it) {
                Reslt.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
                is Reslt.Failure -> {
                    text_error.visibility = View.VISIBLE
                    it.error?.printStackTrace()
                   text_error.text = it.message
                }
               is Reslt.Success -> {
                    text_tweet.text = "Author: ${it.data.name}\nTweet: ${it.data.tweet}"
                }
            }

        })
    }

}