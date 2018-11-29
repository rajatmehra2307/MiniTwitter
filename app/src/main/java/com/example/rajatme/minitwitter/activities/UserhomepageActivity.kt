package com.example.rajatme.minitwitter.activities

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.network.models.Tweet
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.TwitterApplication
import com.example.rajatme.minitwitter.viewModel.UserhomePageViewModel
import com.example.rajatme.minitwitter.adapters.UserTweetAdapter
import com.example.rajatme.minitwitter.databinding.ActivityUserhomepageBinding
import com.example.services.UserhomepageService
import io.reactivex.disposables.Disposable
import com.example.services.Utils.NetworkState
import javax.inject.Inject


class UserhomepageActivity : AppCompatActivity() {
    var profilepic : ImageView ?= null
    var recyclerView : RecyclerView ?= null
    private var dialog: AlertDialog ?= null
    var binding : ActivityUserhomepageBinding?= null

    @Inject
    lateinit var userhomepageViewModel: UserhomePageViewModel


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
        binding!!.constraintLayout.visibility = View.INVISIBLE
        var recyclerViewAdapter = UserTweetAdapter()
        val observer = Observer<PagedList<Tweet>> {
            recyclerViewAdapter.submitList(it)
        }

        val observerNetworkState = Observer<NetworkState.Status> {
            when(it) {
                 NetworkState.Status.RUNNING ->
                    dialog!!.show()
                 NetworkState.Status.SUCCESS -> {
                     dialog!!.dismiss()
                     binding!!.constraintLayout.visibility = View.VISIBLE
                     binding!!.viewModel = userhomepageViewModel
                 }

                 NetworkState.Status.FAILED -> {
                     dialog!!.dismiss()
                     Toast.makeText(this, "Error fetching the results", Toast.LENGTH_SHORT).show()
                     finish()
                 }
            }
        }
        val observerProfilePicUrl = Observer<String> {
            if(it != null) {
                Glide.with(applicationContext)
                    .load(it)
                    .placeholder(R.drawable.ic_action_name)
                    .into(profilepic)
            }
        }

        userhomepageViewModel.state.observe(this,observerNetworkState)
        userhomepageViewModel.profilePicUrl.observe(this,observerProfilePicUrl)

        binding!!.follow.setOnClickListener{
            userhomepageViewModel.followAUser(screenName)

        }
        userhomepageViewModel!!.fetchTweetsbyUser(screenName)!!.observe(this, observer)
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
        userhomepageViewModel.fetchUserInfo(screenName)
    }

}