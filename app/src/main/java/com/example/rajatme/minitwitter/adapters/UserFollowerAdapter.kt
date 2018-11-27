package com.example.rajatme.minitwitter.adapters

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
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
import com.example.rajatme.minitwitter.activities.UserhomepageActivity
import com.example.rajatme.minitwitter.databinding.FollowerListBinding
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
        var binding : FollowerListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.follower_list,viewGroup,false)
        var viewHolder = UserFollowerViewHolder(binding)
        viewHolder.initialize()
        return viewHolder

    }

    inner class UserFollowerViewHolder(var binding : FollowerListBinding) : RecyclerView.ViewHolder(binding.root) {
        private var profilePicImageView: ImageView? = null
        private var userNameTextView: TextView? = null
        private var userHandleTextView: TextView? = null
        private var userDescription: TextView? = null
        private var cardView : CardView ?= null

        @Inject
        lateinit var userhomepageService : UserhomepageService

        fun bind(follower: UserInfo, position: Int) {

            userNameTextView?.setText(follower.name)
            userHandleTextView?.setText("@${follower.handle}")
            userDescription?.setText(follower.description)
            cardView!!.setOnClickListener {
                var intent = Intent(binding.root.context,UserhomepageActivity::class.java)
                intent.putExtra("name",follower.handle)
                binding.root.context.startActivity(intent)
            }

            Glide.with(itemView.context)
                .load(follower.imageUrl)
                .placeholder(R.drawable.ic_action_name)
                .into(profilePicImageView)

        }

        fun initialize() {
            profilePicImageView = binding.profilePic
            userNameTextView = binding.userName
            userDescription = binding.userDescription
            userHandleTextView = binding.userHandle
            cardView = binding.cardView

        }
    }

}