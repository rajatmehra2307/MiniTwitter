package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.database.UserTimelineEntity
import com.example.rajatme.minitwitter.R
//import com.example.network.service.UserTimeLineService
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserTimelineAdapter
import com.example.services.UserTimeLineService
import com.example.services.Utils.OAuthTokenObject
import com.example.services.Utils.PREFERENCE_NAME
import com.example.services.Utils.PREF_KEY_OAUTH_SECRET
import com.example.services.Utils.PREF_KEY_OAUTH_TOKEN

class TimeLineActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter = UserTimelineAdapter()
    var userTimelineViewModel: UserTimelineViewModel? = null
    var swipeRefreshlayout : SwipeRefreshLayout ?= null
    private val userTimeLineService by lazy {
        UserTimeLineService.create(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        recyclerView = findViewById(R.id.recycler_view)
        userTimelineViewModel =
                ViewModelProviders.of(this, UserTimelineViewModelFactory(this)).get(UserTimelineViewModel::class.java)
        val observer = Observer<PagedList<UserTimelineEntity>> {
            recyclerViewAdapter.submitList(it)
        }
        userTimelineViewModel!!.getTimeLine()!!.observe(this, observer)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        swipeRefreshlayout = findViewById(R.id.swipeRefresh)
        swipeRefreshlayout?.setOnRefreshListener{
            userTimeLineService.makeNetworkRequest()
            swipeRefreshlayout?.isRefreshing = false
        }
        fetchTimeline()
    }

    fun fetchTimeline() {
        val sharedPreferences = getSharedPreferences(PREFERENCE_NAME, 0)
        val userToken = sharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, null)
        val userTokenSecret = sharedPreferences.getString(PREF_KEY_OAUTH_SECRET, null)
        val oAuthTokenObject = OAuthTokenObject(userToken, userTokenSecret)
        userTimeLineService.fetchUserTimeLine()

    }
}

//TODO Refactoring the code properly
//TODO Using Glide
//TODO Adding activity for clicking urls
//TODO Seeing followers and following
//TODO Searching tweets by hashtag
//TODO adding alert bar on login with twitter
//TODO(3) Fetching content from the Twitter4j text url
//TODO(4) Adding actions to retweets and tweets
//TODO(5) Adding a new FAB for posting tweets and create tweet activity
// TODO(6) Search twitter activity
//TODO(7) adding Dagger injection
// TODO(8) Adding follow a user functionality
//TODO(9) Edit profile functionality
//TODO(10) Swipe to refresh functionality
//TODO(11) Fetching data from DB on the first call

