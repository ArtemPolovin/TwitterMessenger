package com.example.twittermessenger.ui.twit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.HomeTimelineModel
import com.example.twittermessenger.R
import com.example.twittermessenger.common.loadCircleImage
import com.example.twittermessenger.common.loadImage


class TwitAdapter : RecyclerView.Adapter<TwitAdapter.TwitHolder>() {
    private val homeTimelineModelList = mutableListOf<HomeTimelineModel>()
    private lateinit var onClickImageListener: OnClickImageListener


    fun setData(newHomeTimelineModelList: List<HomeTimelineModel>) {
        homeTimelineModelList.clear()
        homeTimelineModelList.addAll(newHomeTimelineModelList)
        notifyDataSetChanged()
    }

    fun onClickImage(onClickListener: OnClickImageListener) {
        onClickImageListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_twit, parent, false)
        return TwitHolder(view, parent.context, onClickImageListener)
    }

    override fun onBindViewHolder(holder: TwitHolder, position: Int) {
        holder.bind(homeTimelineModelList[position])
    }

    override fun getItemCount(): Int {
        return homeTimelineModelList.size
    }

    class TwitHolder(
        itemView: View,
        private val context: Context,
        private val onClickImageListener: OnClickImageListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val imageProfile = itemView.findViewById<ImageView>(R.id.image_profile)
        private val userName = itemView.findViewById<TextView>(R.id.text_name)
        private val userNickname = itemView.findViewById<TextView>(R.id.text_nickname)
        private val publishedDate = itemView.findViewById<TextView>(R.id.text_published_date)
        private val twitText = itemView.findViewById<TextView>(R.id.text_twit)
        private val attachedImage1 = itemView.findViewById<ImageView>(R.id.attached_image1)
        private val attachedImage2 = itemView.findViewById<ImageView>(R.id.attached_image2)
        private val attachedImage3 = itemView.findViewById<ImageView>(R.id.attached_image3)
        private val attachedImage4 = itemView.findViewById<ImageView>(R.id.attached_image4)

        private val cardView = itemView.findViewById<CardView>(R.id.card_view)



        fun bind(homeTimelineModel: HomeTimelineModel) {

            homeTimelineModel.run {
                loadCircleImage(imageProfile, profileImageURL,context)
                userName.text = name
                userNickname.text = nickname
                publishedDate.text = publishedTwitDate
                twitText.text = tweet
                setupAttachedImages(attachedImages)
            }

            homeTimelineModel.attachedImages?.let {imageList ->
                attachedImage1.setOnClickListener {
                    onClickImageListener.getAttachedImage(attachedImage1, imageList[0])
                }
                attachedImage2.setOnClickListener {
                    onClickImageListener.getAttachedImage(attachedImage2, imageList[1])
                }
                attachedImage3.setOnClickListener {
                    onClickImageListener.getAttachedImage(attachedImage3, imageList[2])
                }
                attachedImage4.setOnClickListener {
                    onClickImageListener.getAttachedImage(attachedImage4, imageList[3])
                }

            }



        }

        private fun setupAttachedImages(imageUrlList: List<String>?) {
            if (imageUrlList == null) return

            cardView.visibility = View.VISIBLE

            when (imageUrlList.size) {
                1 -> {
                    setImageSize(attachedImage1, imageUrlList[0], 337, 165)
                }
                2 -> {
                    setImageSize(attachedImage1, imageUrlList[0], 167, 165)
                    setImageSize(attachedImage2, imageUrlList[1], 167, 165)
                }
                3 -> {
                    setImageSize(attachedImage1, imageUrlList[0], 167, 165)
                    setImageSize(attachedImage2, imageUrlList[1], 167, 81)
                    setImageSize(attachedImage3, imageUrlList[2], 167, 81)
                }
                4 -> {
                    setImageSize(attachedImage1, imageUrlList[0], 167, 81)
                    setImageSize(attachedImage2, imageUrlList[1], 167, 81)
                    setImageSize(attachedImage3, imageUrlList[2], 167, 81)
                    setImageSize(attachedImage4, imageUrlList[3], 167, 81)
                }
            }

        }

        private fun setImageSize(
            image: ImageView,
            imageUrl: String,
            viewWidth: Int,
            viewHeight: Int
        ) {
            val scale = context.resources.displayMetrics.density
            image.apply {
                visibility = View.VISIBLE
                requestLayout()
                layoutParams.width = (viewWidth * scale).toInt()
                layoutParams.height = (viewHeight * scale).toInt()
                loadImage(this, imageUrl,context)
            }
        }

    }

    interface OnClickImageListener {
        fun getAttachedImage(image: ImageView, imageUrl: String)
    }


}