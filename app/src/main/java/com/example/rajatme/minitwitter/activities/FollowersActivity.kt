package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserFollowerAdapter
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