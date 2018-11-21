package com.example.rajatme.minitwitter.adapters

import android.content.Intent
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
import com.example.services.Utils.HASHTAG
import com.example.services.Utils.MENTION
import com.tylersuehr.socialtextview.SocialTextView
import java.text.SimpleDateFormat

class UserTweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var image: ImageView? = null
    private var userNameView: TextView? = null
    private var updateTimeView: TextView? = null
    private var tweetTextView: SocialTextView? = null
    private var retweetButton: Button? = null
    private var replyButton: Button? = null
    private val TAG = "UserTimeLineAdapter"

    fun initialise() {
        image = itemView.findViewById(R.id.profile_img)
        userNameView = itemView.findViewById(R.id.userScreen)
        updateTimeView = itemView.findViewById(R.id.updateTime)
        tweetTextView = itemView.findViewById(R.id.updateText)
        retweetButton = itemView.findViewById(R.id.retweet)
        replyButton = itemView.findViewById(R.id.reply)
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

                }
            }

        }
        tweetTextView!!.setOnLinkClickListener(listener)

    }

    fun bind(tweet : Tweet) {
        var tweetText : String
        userNameView!!.setText(tweet.user.screen_name)
        if(tweet.retweeted_status != null)
            tweetText = tweet!!.retweeted_status!!.full_text
        else
            tweetText = tweet.full_text
        tweetTextView?.setLinkText(tweetText)
        var timeCreatedAt = tweet.created_at
        var timeCreatedFormatted = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
        var date = timeCreatedFormatted.parse(timeCreatedAt)
        var epoch = date.time
        updateTimeView!!.setText(DateUtils.getRelativeTimeSpanString(epoch))
        Glide.with(itemView.context)
            .load(tweet.user.profile_image_url)
            .into(image)

    }

}