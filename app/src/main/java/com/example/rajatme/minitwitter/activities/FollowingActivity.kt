package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.services.Utils.NetworkState

class FollowingActivity : FollowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel =
            ViewModelProviders.of(this, UserTweetViewModelFactory(screenName)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<UserInfo>> {
            recyclerViewAdapter.submitList(it)
        }
        val networkObserver = Observer<NetworkState> {
            setProgressDialog(it)
        }
        viewModel!!.getFriendsOfAUser()!!.observe(this, observer)
        viewModel!!.getNetworkState().observe(this,networkObserver)

    }
    override fun getInfoaboutUser(screenName: String) {
        userhomepageService.fetchFriendsOfUser(screenName)
    }

}