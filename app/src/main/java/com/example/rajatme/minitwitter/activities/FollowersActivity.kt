package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserFollowerAdapter

class FollowersActivity : FollowActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel =
            ViewModelProviders.of(this, UserTweetViewModelFactory(screenName)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<UserInfo>> {
            recyclerViewAdapter.submitList(it)
        }
        viewModel!!.getFollowersOfAUser()!!.observe(this, observer)

    }

    override fun getInfoaboutUser(screenName: String) {
        userhomepageService.fetchFollowersOfUser(screenName)
    }

}