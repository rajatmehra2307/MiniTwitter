package com.example.rajatme.minitwitter.adapters

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.network.models.Tweet
import com.example.rajatme.minitwitter.activities.Hashtag
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.activities.UserhomepageActivity
import com.example.rajatme.minitwitter.databinding.TweetTimelineBinding
import com.example.rajatme.minitwitter.databindingmodel.TweetDataBinding
import com.example.services.Utils.HASHTAG
import com.example.services.Utils.MENTION
import com.example.services.Utils.URL
import com.tylersuehr.socialtextview.SocialTextView
import java.text.SimpleDateFormat

class UserTweetViewHolder(var binding : TweetTimelineBinding) : RecyclerView.ViewHolder(binding.root) {

    private var tweetTextView: SocialTextView? = null
    private var retweetButton: Button? = null
    private var replyButton: Button? = null
    private val TAG = "UserTimeLineAdapter"

    fun initialise() {
        tweetTextView = binding.updateText
        retweetButton = binding.retweet
        replyButton = binding.reply
        var listener = SocialTextView.OnLinkClickListener { i: Int, s: String ->
            when (i) {
                HASHTAG -> {
                    val intent = Intent(itemView.context, Hashtag::class.java)
                    intent.putExtra("name", s)
                    itemView.context.startActivity(intent)
                }

                MENTION -> {
                    val intent = Intent(itemView.context, UserhomepageActivity::class.java)
                    intent.putExtra("name", s)
                    itemView.context.startActivity(intent)
                }
                else -> {
                    itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(s)))
                }
            }

        }
        tweetTextView!!.setOnLinkClickListener(listener)

    }

    fun bind(tweet : Tweet) {
        var tweetText : String
        if(tweet.retweeted_status != null)
            tweetText = tweet!!.retweeted_status!!.full_text
        else
            tweetText = tweet.full_text

        var timeCreatedAt = tweet.created_at
        var timeCreatedFormatted = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
        var date = timeCreatedFormatted.parse(timeCreatedAt)
        var epoch = date.time

        var tweetDataBinding = TweetDataBinding(tweet.user.profile_image_url,tweet.user.screen_name,(DateUtils.getRelativeTimeSpanString(epoch)).toString())
        binding.tweet = tweetDataBinding
        binding.updateText.setLinkText(tweetText)
        Glide.with(itemView.context)
            .load(tweet.user.profile_image_url)
            .placeholder(R.drawable.ic_action_name)
            .into(binding.profileImg)

    }

}