package com.example.rajatme.minitwitter.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.services.UserhomepageService

class UserFollowerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    private var profilePicImageView : ImageView?= null
    private var userNameTextView : TextView ?= null
    private var userHandleTextView: TextView ?= null
    private var userDescription : TextView ?= null
    private var followButton : Button?= null
    private var userhomepageService = UserhomepageService()

    fun bind(follower : UserInfo) {
        followButton!!.isEnabled = true
        userNameTextView?.setText(follower.name)
        userHandleTextView?.setText(follower.handle)
        userDescription?.setText(follower.description)
        followButton?.setOnClickListener{
            userhomepageService.followAUser(follower.handle)
            followButton?.isEnabled = false
        }
        Glide.with(itemView.context)
            .load(follower.imageUrl)
            .into(profilePicImageView)
    }

    fun initialize() {
        profilePicImageView = itemView.findViewById(R.id.profilePic)
        userNameTextView = itemView.findViewById(R.id.userName)
        userDescription = itemView.findViewById(R.id.userDescription)
        userHandleTextView = itemView.findViewById(R.id.userHandle)
        followButton = itemView.findViewById(R.id.follow)

    }


}