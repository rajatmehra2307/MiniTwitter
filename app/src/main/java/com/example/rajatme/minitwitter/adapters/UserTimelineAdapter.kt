package com.example.rajatme.minitwitter.adapters

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.rajatme.minitwitter.Database.UserTimelineEntity
import com.example.rajatme.minitwitter.R
import kotlinx.android.synthetic.main.tweet_timeline.view.*

class UserTimelineAdapter : RecyclerView.Adapter<UserTimelineAdapter.UserTimeLineViewHolder>() {

    private var userTimeline : List<UserTimelineEntity> ?= null

    class UserTimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image : ImageView ?= null
        private var userNameView : TextView ?= null
        private var updateTimeView : TextView ?= null
        private var tweetText : TextView ?= null
        private var retweetButton : Button ?= null
        private var replyButton : Button ?= null

        fun initialise() {
            image = itemView.findViewById(R.id.profile_img)
            userNameView = itemView.findViewById(R.id.userScreen)
            updateTimeView = itemView.findViewById(R.id.updateTime)
            tweetText = itemView.findViewById(R.id.updateText)
            retweetButton = itemView.findViewById(R.id.retweet)
            replyButton = itemView.findViewById(R.id.reply)

        }

        fun bind(userTimelineEntity: UserTimelineEntity) {
            userNameView!!.setText(userTimelineEntity.userName)
            tweetText!!.setText(userTimelineEntity.updateText)
            val createdAt = userTimelineEntity.timeCreated
            updateTimeView!!.setText(DateUtils.getRelativeTimeSpanString(createdAt!!))


        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserTimeLineViewHolder {
        val context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.tweet_timeline,viewGroup,false)
        val userTimeLineViewHolder = UserTimeLineViewHolder(view)
        userTimeLineViewHolder.initialise()
        return userTimeLineViewHolder

    }

    override fun getItemCount(): Int {
      if(userTimeline != null) return userTimeline!!.size
        return 0
    }

    override fun onBindViewHolder(userTimeLineViewHolder: UserTimeLineViewHolder, postion: Int) {
       userTimeLineViewHolder.bind(userTimeline!!.get(postion))
    }
}