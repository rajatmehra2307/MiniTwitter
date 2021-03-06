package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.viewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.viewModel.UserTweetViewModelFactory
import com.example.services.Utils.NetworkState

class FollowersActivity : FollowActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel =
            ViewModelProviders.of(this, UserTweetViewModelFactory(screenName,userhomepageService)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<UserInfo>> {
            recyclerViewAdapter.submitList(it)
        }
        val networkObserver = Observer<NetworkState> {
            setProgressDialog(it)
        }
        viewModel!!.getFollowersOfAUser()!!.observe(this, observer)
        viewModel!!.getNetworkState().observe(this,networkObserver)

    }

    override fun getInfoaboutUser(screenName: String) {
        userhomepageService.fetchFollowersOfUser(screenName)
    }



}