package com.example.rajatme.minitwitter.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.database.UserTimelineEntity
import com.example.rajatme.minitwitter.activities.Hashtag
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.activities.UserhomepageActivity
import com.example.rajatme.minitwitter.databinding.TweetTimelineBinding
import com.example.rajatme.minitwitter.databindingmodel.TweetDataBinding
import com.example.services.Utils.HASHTAG
import com.example.services.Utils.MENTION
import com.tylersuehr.socialtextview.SocialTextView

class UserTimelineViewHolder(var binding : TweetTimelineBinding) : RecyclerView.ViewHolder(binding.root) {

    private var tweetText: SocialTextView? = null
    private var retweetButton: Button? = null
    private var replyButton: Button? = null
    private val TAG = "UserTimeLineAdapter"

    fun initialise() {
        tweetText = binding.updateText
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

                }
            }

        }
        tweetText!!.setOnLinkClickListener(listener)

    }

    fun bind(userTimelineEntity: UserTimelineEntity) {
        binding.updateText.setLinkText(userTimelineEntity.updateText)
        val createdAt = userTimelineEntity.timeCreated
        var tweetDataBinding = TweetDataBinding(userTimelineEntity.imageUrl,userTimelineEntity.userName!!,(DateUtils.getRelativeTimeSpanString(createdAt!!)).toString())
        binding.tweet = tweetDataBinding
        Glide.with(itemView.context)
            .load(userTimelineEntity.imageUrl)
            .placeholder(R.drawable.ic_action_name)
            .into(binding.profileImg)
    }


}