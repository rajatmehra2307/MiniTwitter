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
            .setTweetModeExtended(true)
            .build()
        val twitter = TwitterFactory(cb).instance
        Thread {
             val statuses : List<Status> = twitter.getHomeTimeline()
            for(status in statuses) {
                var updateText : String ?= null
                if(status.retweetedStatus != null)
                    updateText = status.retweetedStatus.text
                else
                    updateText = status.text
                val userTimelineEntity = UserTimelineEntity(updateText,status.user.screenName,status.createdAt.time,status.user.profileImageURL.toString())
                userTimelineViewModel!!.insert(userTimelineEntity)
            }
        }.start()
    }
}

//TODO(1) Adding clickable to mentions and hashtags
// TODO(2) Making the timeline paginated
//TODO(3) Fetching content from the Twitter4j text url
//TODO(4) Adding actions to retweets and tweets
//TODO(5) Adding a new FAB for posting tweets and create tweet activity
// TODO(6) Search twitter activity
//TODO(7) adding Dagger injection
// TODO(8) Adding follow a user functionality
//TODO(9) Edit profile functionality
//TODO(10) Swipe to refresh functionality
//TODO(11) Fetching data from DB on the first call

