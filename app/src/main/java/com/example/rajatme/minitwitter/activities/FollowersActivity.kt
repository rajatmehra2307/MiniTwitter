package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.support.v7.widget.RecyclerView.VERTICAL
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserFollowerAdapter
import com.example.services.UserhomepageService

class FollowersActivity : AppCompatActivity() {

    private var recyclerView : RecyclerView ?= null
    var userhomepageService = UserhomepageService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        val screenName = intent.getStringExtra("name")
        recyclerView = findViewById(R.id.recycler_view)
        var recyclerViewAdapter = UserFollowerAdapter()
        var viewModel =
            ViewModelProviders.of(this, UserTweetViewModelFactory(screenName)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<UserInfo>> {
            recyclerViewAdapter.submitList(it)
        }
        viewModel!!.getFollowersOfAUser()!!.observe(this, observer)
        var itemDecorator = DividerItemDecoration(this, VERTICAL)
        recyclerView!!.addItemDecoration(itemDecorator)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        userhomepageService.fetchFollowersOfUser(screenName)

    }
}