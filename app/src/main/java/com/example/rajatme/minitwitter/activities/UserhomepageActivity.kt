package com.example.rajatme.minitwitter.activities

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.network.models.LookUpResult
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.databindingmodel.TwitterUser
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.TwitterApplication
import com.example.rajatme.minitwitter.Utils.formatNumber
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTweetViewModelFactory
import com.example.rajatme.minitwitter.ViewModel.UserhomePageViewModel
import com.example.rajatme.minitwitter.adapters.UserTweetAdapter
import com.example.rajatme.minitwitter.databinding.ActivityUserhomepageBinding
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.example.services.UserhomepageService
import com.example.services.Utils.UserConnection
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class UserhomepageActivity : AppCompatActivity() {
    var profilepic : ImageView ?= null
    var recyclerView : RecyclerView ?= null
    private var disposable: Disposable? = null
    private var dialog: AlertDialog ?= null
    var binding : ActivityUserhomepageBinding?= null

    @Inject
    lateinit var userhomepageService : UserhomepageService


    override fun onCreate(savedInstanceState: Bundle?) {

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder!!.setView(R.layout.layout_loading_dialog)
        (application as TwitterApplication).component!!.inject(this)
        dialog = builder.create()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_userhomepage)
        var screenName = intent.getStringExtra("name")
        screenName = screenName.substring(screenName.indexOf("@") + 1)
        profilepic = binding?.profilePic
        recyclerView = binding?.recyclerViewTweet
        binding!!.follow.isEnabled = false
        var recyclerViewAdapter = UserTweetAdapter()
        var userTweetViewModel =
                ViewModelProviders.of(this, UserTweetViewModelFactory(screenName,userhomepageService)).get(UserTweetViewModel::class.java)
        val observer = Observer<PagedList<Tweet>> {
            recyclerViewAdapter.submitList(it)
        }
        binding!!.follow.setOnClickListener{
            userhomepageService.followAUser(screenName)
            binding!!.follow.isEnabled = false
        }
        userTweetViewModel!!.getTweetByUser()!!.observe(this, observer)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        binding!!.follower?.setOnClickListener {
            val intent = Intent(this, FollowersActivity::class.java)
            intent.putExtra("name", screenName)
            startActivity(intent)
        }
        binding!!.following?.setOnClickListener {
            val intent = Intent(this, FollowingActivity::class.java)
            intent.putExtra("name", screenName)
            startActivity(intent)
        }
        fetchUserInfo(screenName)
    }

    fun fetchUserInfo(screen_id : String) {
        dialog!!.show()
        disposable = userhomepageService.fetchUserInfo(screen_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                dialog!!.dismiss()
                fetchConnection(screen_id,result)

                },
                { error -> Log.e("Fetching userInfo ->",error.message)
                    dialog!!.dismiss()
                    Toast.makeText(this,"There was an error fetching the result",Toast.LENGTH_SHORT).show()
                    finish()

                })
        userhomepageService.fetchTweetsbyUser(screen_id)

    }


    fun fetchConnection(screen_id: String, userInfo: UserInfo?) {
        userhomepageService.lookUpConnection(screen_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                displayButton(result)
                displayInfo(userInfo)
            }

    }

    private fun displayButton(lookUpResultList: List<LookUpResult>) {
        if(lookUpResultList != null && !lookUpResultList.isEmpty()) {
            val lookUpResult = lookUpResultList.get(0)
            val listConnection = mutableListOf<UserConnection>()
            for (connection in lookUpResult.connection) {
                listConnection.add(UserConnection.valueOf(connection.toUpperCase()))
            }
            if (listConnection.contains(UserConnection.NONE)) {
                binding!!.follow.isEnabled = true
            }
        }
    }


    private fun displayInfo(userInfo: UserInfo?) {
        var twitterUser = TwitterUser(userInfo?.name, userInfo?.handle, userInfo?.description, userInfo?.imageUrl,"${formatNumber(userInfo!!.followers)} followers",
            "${formatNumber(userInfo!!.friends)} following")
        Glide.with(applicationContext)
            .load(userInfo?.imageUrl)
            .placeholder(R.drawable.ic_action_name)
            .into(profilepic)
        binding!!.twitterUser = twitterUser

    }
}