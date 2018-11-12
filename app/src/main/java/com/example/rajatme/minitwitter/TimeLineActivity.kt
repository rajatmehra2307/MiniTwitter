package com.example.rajatme.minitwitter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.rajatme.minitwitter.Database.UserTimelineEntity
import com.example.rajatme.minitwitter.Utils.*
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModel
import com.example.rajatme.minitwitter.adapters.UserTimelineAdapter
import twitter4j.Status
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class TimeLineActivity : AppCompatActivity(){
    var recyclerView : RecyclerView ?= null
    var recyclerViewAdapter = UserTimelineAdapter()
    var userTimelineViewModel : UserTimelineViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        recyclerView = findViewById(R.id.recycler_view)
        userTimelineViewModel = ViewModelProviders.of(this).get(UserTimelineViewModel::class.java)
        val observer = Observer<List<UserTimelineEntity>> {
            recyclerViewAdapter.setUserTimeLine(it)
        }
        userTimelineViewModel!!.getTimeline().observe(this,observer)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        fetchTimeline()
    }
    fun fetchTimeline() {
        val sharedPreferences = getSharedPreferences(PREFERENCE_NAME,0)
        val userToken = sharedPreferences.getString(PREF_KEY_OAUTH_TOKEN,null)
        val userTokenSecret = sharedPreferences.getString(PREF_KEY_OAUTH_SECRET,null)
        val cb = ConfigurationBuilder().setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
            .setOAuthConsumerSecret(TWITTER_CONSUMER_KEY_SECRET)
            .setOAuthAccessToken(userToken)
            .setOAuthAccessTokenSecret(userTokenSecret)
            .build()
        val twitter = TwitterFactory(cb).instance
        Thread {
            val statuses : List<Status> = twitter.getHomeTimeline()
            for(status in statuses) {
                val userTimelineEntity = UserTimelineEntity(status.text,status.user.screenName,status.createdAt.time,status.user.profileImageURL.toString())
                userTimelineViewModel!!.insert(userTimelineEntity)
            }
        }.start()
    }
}
