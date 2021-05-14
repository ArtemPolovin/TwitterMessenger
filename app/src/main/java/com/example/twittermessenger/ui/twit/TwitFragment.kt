package com.example.twittermessenger.ui.twit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.common.Reslt
import com.example.twittermessenger.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tweet.*


@AndroidEntryPoint
class TwitFragment : Fragment() {

    private val viewModel: TwitViewModel by viewModels()
    private lateinit var twitAdapter: TwitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        twitAdapter = TwitAdapter()

        rv_twits.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = twitAdapter
        }

        twitAdapter.onClickImage(object : TwitAdapter.OnClickImageListener {
            override fun getAttachedImage(image: ImageView, imageUrl: String) {
                openImageInFragment(image, imageUrl)
            }
        })

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
                    twitAdapter.setData(it.data)
                }
            }

        })
    }

    private fun openImageInFragment(image: ImageView, imageUrl: String) {
        setFragmentResult("requestKey", bundleOf("image_url" to imageUrl))

        val extras = FragmentNavigatorExtras(image to "image_big")

        findNavController().navigate(R.id.action_tweet_fragment_to_twit_image,null,null, extras)

    }


}