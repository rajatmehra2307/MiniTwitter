package com.example.rajatme.minitwitter.adapters

import android.arch.paging.PagedListAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.example.database.UserTimelineEntity
import com.example.rajatme.minitwitter.R
import java.io.InputStream
import java.net.URL

class UserTimelineAdapter :
    PagedListAdapter<UserTimelineEntity, UserTimelineViewHolder>(TIMELINE_COMPARATOR) {

    companion object {
        private val TIMELINE_COMPARATOR = object : DiffUtil.ItemCallback<UserTimelineEntity>() {
            override fun areItemsTheSame(oldItem: UserTimelineEntity, newItem: UserTimelineEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserTimelineEntity, newItem: UserTimelineEntity): Boolean =
                oldItem == newItem
        }
    }

    

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserTimelineViewHolder {
        val context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.tweet_timeline, viewGroup, false)
        val userTimeLineViewHolder = UserTimelineViewHolder(view)
        userTimeLineViewHolder.initialise()
        return userTimeLineViewHolder

    }

    override fun onBindViewHolder(userTimeLineViewHolder: UserTimelineViewHolder, postion: Int) {
        getItem(postion)?.let {
            userTimeLineViewHolder.bind(it)
        }
    }


    class ImageDownloaderAsyncTask(var imageView: ImageView?) : AsyncTask<String, Void, Bitmap?>() {
        override fun onPostExecute(result: Bitmap?) {
            imageView!!.setImageBitmap(result)
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            var image: Bitmap
            var url = URL(params[0])
            var input = url.getContent() as InputStream?
            image = BitmapFactory.decodeStream(input)
            return image
        }

    }
}