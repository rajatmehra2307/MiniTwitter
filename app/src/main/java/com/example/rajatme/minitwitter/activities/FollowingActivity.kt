package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory

class FollowingActivity : FollowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel =
            ViewModelProviders.of(this, UserTweetViewModelFactory(screenName)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<UserInfo>> {
            recyclerViewAdapter.submitList(it)
        }
        viewModel!!.getFriendsOfAUser()!!.observe(this, observer)

    }
    override fun getInfoaboutUser(screenName: String) {
        userhomepageService.fetchFriendsOfUser(screenName)
    }

}