package com.example.rajatme.minitwitter.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.services.UserhomepageService
import javax.inject.Inject


class UserFollowerAdapter : PagedListAdapter<UserInfo,UserFollowerAdapter.UserFollowerViewHolder>(USER_COMPARATOR) {

    private var stateOfView = mutableSetOf<Int>()

    override fun onBindViewHolder(userFollowerViewHolder: UserFollowerViewHolder , position: Int) {
        getItem(position)?.let {
            userFollowerViewHolder.bind(it,position)
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserInfo>() {
            override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserFollowerViewHolder {
        val context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.follower_list, viewGroup, false)
        var viewHolder = UserFollowerViewHolder(view)
        viewHolder.initialize()
        return viewHolder

    }

    inner class UserFollowerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var profilePicImageView: ImageView? = null
        private var userNameTextView: TextView? = null
        private var userHandleTextView: TextView? = null
        private var userDescription: TextView? = null
        private var followButton: Button? = null

        @Inject
        lateinit var userhomepageService : UserhomepageService

        fun bind(follower: UserInfo, position: Int) {
            followButton!!.isEnabled = true
            if (stateOfView.contains(position))
                followButton!!.isEnabled = false
            userNameTextView?.setText(follower.name)
            userHandleTextView?.setText("@${follower.handle}")
            userDescription?.setText(follower.description)
            followButton?.setOnClickListener {
                userhomepageService.followAUser(follower.handle)
                followButton?.isEnabled = false
                stateOfView.add(position)
            }
            Glide.with(itemView.context)
                .load(follower.imageUrl)
                .placeholder(R.drawable.ic_action_name)
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

}