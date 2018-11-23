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
import com.example.services.Utils.HASHTAG
import com.example.services.Utils.MENTION
import com.tylersuehr.socialtextview.SocialTextView

class UserTimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var image: ImageView? = null
    private var userNameView: TextView? = null
    private var updateTimeView: TextView? = null
    private var tweetText: SocialTextView? = null
    private var retweetButton: Button? = null
    private var replyButton: Button? = null
    private val TAG = "UserTimeLineAdapter"

    fun initialise() {
        image = itemView.findViewById(R.id.profile_img)
        userNameView = itemView.findViewById(R.id.userScreen)
        updateTimeView = itemView.findViewById(R.id.updateTime)
        tweetText = itemView.findViewById(R.id.updateText)
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
        tweetText!!.setOnLinkClickListener(listener)

    }

    fun bind(userTimelineEntity: UserTimelineEntity) {
        userNameView!!.setText(userTimelineEntity.userName)
        tweetText!!.setLinkText(userTimelineEntity.updateText)
        val createdAt = userTimelineEntity.timeCreated
        updateTimeView!!.setText(DateUtils.getRelativeTimeSpanString(createdAt!!))
        Glide.with(itemView.context)
            .load(userTimelineEntity.imageUrl)
            .placeholder(R.drawable.ic_action_name)
            .into(image)
    }


}