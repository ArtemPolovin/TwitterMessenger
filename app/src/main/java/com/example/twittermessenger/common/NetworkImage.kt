package com.example.twittermessenger.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun loadImage(image: ImageView, imageUrl: String, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .into(image)
}

fun loadCircleImage(image: ImageView, imageUrl: String, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .apply(RequestOptions.circleCropTransform())
        .into(image)
}
