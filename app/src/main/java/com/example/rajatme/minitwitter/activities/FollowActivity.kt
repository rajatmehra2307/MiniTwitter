package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserFollowerAdapter
import com.example.services.UserhomepageService

abstract class FollowActivity : AppCompatActivity() {
    protected var recyclerView : RecyclerView ?= null
    protected var userhomepageService = UserhomepageService()
    protected lateinit var screenName : String
    protected var recyclerViewAdapter = UserFollowerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        recyclerView = findViewById(R.id.recycler_view)
        screenName = intent.getStringExtra("name")
        var itemDecorator = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView!!.addItemDecoration(itemDecorator)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        getInfoaboutUser(screenName)
    }

    abstract fun getInfoaboutUser(screenName : String)
}