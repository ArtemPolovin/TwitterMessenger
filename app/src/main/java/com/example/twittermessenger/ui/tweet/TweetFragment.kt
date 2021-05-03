package com.example.twittermessenger.ui.tweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.domain.common.Status
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

            when (it.status) {
                Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    text_tweet.text = it.message
                }
                Status.SUCCESS ->{
                    text_tweet.text = "Author: ${it.data?.name}\nTweet: ${it.data?.tweet}"
                }
            }

        })
    }

}