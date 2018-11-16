package com.example.rajatme.minitwitter.adapters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.renderscript.ScriptGroup
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.rajatme.minitwitter.Database.UserTimelineEntity
import com.example.rajatme.minitwitter.Hashtag
import com.example.rajatme.minitwitter.MentionActivity
import com.example.rajatme.minitwitter.R
import com.tylersuehr.socialtextview.SocialTextView
import kotlinx.android.synthetic.main.tweet_timeline.view.*
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class UserTimelineAdapter : RecyclerView.Adapter<UserTimelineAdapter.UserTimeLineViewHolder>() {

    private var userTimeline : List<UserTimelineEntity> ?= null

    class UserTimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image : ImageView ?= null
        private var userNameView : TextView ?= null
        private var updateTimeView : TextView ?= null
        private var tweetText : SocialTextView ?= null
        private var retweetButton : Button ?= null
        private var replyButton : Button ?= null
        private val TAG = "UserTimeLineAdapter"

        fun initialise() {
            image = itemView.findViewById(R.id.profile_img)
            userNameView = itemView.findViewById(R.id.userScreen)
            updateTimeView = itemView.findViewById(R.id.updateTime)
            tweetText = itemView.findViewById(R.id.updateText)
            retweetButton = itemView.findViewById(R.id.retweet)
            replyButton = itemView.findViewById(R.id.reply)
            var listener = SocialTextView.OnLinkClickListener{ i: Int, s: String ->
                when(i) {
                    1 -> {
                        val intent = Intent(itemView.context,Hashtag::class.java)
                        intent.putExtra("name",s)
                        itemView.context.startActivity(intent)
                    }

                    2-> {
                        val intent = Intent(itemView.context,MentionActivity::class.java)
                        intent.putExtra("name",s)
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
            ImageDownloaderAsyncTask(image).execute(userTimelineEntity.imageUrl)
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

    fun setUserTimeLine(userTimelineList : List<UserTimelineEntity>?) {
        userTimeline = userTimelineList
        notifyDataSetChanged()
    }


    class ImageDownloaderAsyncTask(var imageView: ImageView?) : AsyncTask<String,Void,Bitmap?>() {
        override fun onPostExecute(result: Bitmap?) {
            imageView!!.setImageBitmap(result)
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            var image : Bitmap
            var url = URL(params[0])
            var input = url.getContent() as InputStream?
            image = BitmapFactory.decodeStream(input)
            return image
        }

    }
}