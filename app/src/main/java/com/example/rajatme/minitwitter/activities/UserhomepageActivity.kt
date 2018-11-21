package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.Utils.formatNumber
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserTweetAdapter
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.example.services.UserhomepageService
import io.reactivex.android.schedulers.AndroidSchedulers


class UserhomepageActivity : AppCompatActivity() {
    var userNameTextView : TextView ?= null
    var userHandleTextView : TextView ?= null
    var userDescription : TextView ?= null
    var profilepic : ImageView ?= null
    var followersTextView : TextView ?= null
    var followingTextView : TextView ?= null
    var recyclerView : RecyclerView ?= null
    var userhomepageService = UserhomepageService()
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userhomepage)
        var screenName = intent.getStringExtra("name")
        screenName = screenName.substring(screenName.indexOf("@") + 1)
        userNameTextView = findViewById(R.id.userName)
        userHandleTextView = findViewById(R.id.userHandle)
        profilepic = findViewById(R.id.profilePic)
        userDescription = findViewById(R.id.userDescription)
        followersTextView = findViewById(R.id.follower)
        followingTextView = findViewById(R.id.following)
        recyclerView = findViewById(R.id.recycler_view_tweet)
        var recyclerViewAdapter = UserTweetAdapter()
        var userTweetViewModel =
                ViewModelProviders.of(this, UserTweetViewModelFactory(screenName)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<Tweet>> {
            recyclerViewAdapter.submitList(it)
        }
        userTweetViewModel!!.getTweetByUser()!!.observe(this, observer)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)


        fetchUserInfo(screenName)
    }

    fun fetchUserInfo(screen_id : String) {
        val userInfo = userhomepageService.fetchUserInfo(screen_id)
        disposable = userInfo.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                displayInfo(result)
                },
                {error -> Log.e("Fetching userInfo ->",error.message)
                })
        userhomepageService.fetchTweetsbyUser(screen_id)
    }

    private fun displayInfo(userInfo: UserInfo?) {
        userNameTextView?.setText(userInfo?.name)
        userHandleTextView?.setText("@${userInfo?.handle}")
        Glide.with(applicationContext)
            .load(userInfo?.imageUrl)
            .into(profilepic)
        userDescription?.setText(userInfo?.description)
        followersTextView?.setText("${formatNumber(userInfo!!.followers)} followers")
        followingTextView?.setText("${formatNumber(userInfo!!.friends)} following")
    }
}