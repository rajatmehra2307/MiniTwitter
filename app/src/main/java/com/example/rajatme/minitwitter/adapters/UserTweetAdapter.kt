package com.example.rajatme.minitwitter.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.network.models.Tweet
import com.example.rajatme.minitwitter.R

class UserTweetAdapter : PagedListAdapter<Tweet,UserTweetViewHolder>(TWEET_COMPARATOR){
    override fun onBindViewHolder(userTweetViewHolder: UserTweetViewHolder, position: Int) {
        getItem(position)?.let {
            userTweetViewHolder.bind(it)
        }
    }

    companion object {
        private val TWEET_COMPARATOR = object : DiffUtil.ItemCallback<Tweet>() {
            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserTweetViewHolder {
        val context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.tweet_timeline, viewGroup, false)
        val userTweetViewHolder = UserTweetViewHolder(view)
        userTweetViewHolder.initialise()
        return userTweetViewHolder

    }
}