package com.example.rajatme.minitwitter.databindingmodel

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.rajatme.minitwitter.R

@BindingAdapter("imageUrl")
fun setimageUrl(imageView: ImageView, url : String?) {
    if(url == null || url.isEmpty())
        imageView.setImageDrawable(null)
    else {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_action_name)
            .into(imageView)
    }
}