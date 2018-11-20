package com.example.rajatme.minitwitter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import com.example.database.TimelineCache
import com.example.database.UserTimelineEntity
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
//import com.example.network.service.UserTimeLineService
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserTimelineAdapter
import com.example.services.UserTimeLineService
import com.example.services.Utils.OAuthTokenObject
import com.example.services.Utils.PREFERENCE_NAME
import com.example.services.Utils.PREF_KEY_OAUTH_SECRET
import com.example.services.Utils.PREF_KEY_OAUTH_TOKEN
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import twitter4j.Status
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import java.text.SimpleDateFormat
import java.util.*

class TimeLineActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter = UserTimelineAdapter()
    var userTimelineViewModel: UserTimelineViewModel? = null
    var swipeRefreshlayout : SwipeRefreshLayout ?= null
    private var disposable: Disposable? = null
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
//        var swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener(){
//            userTimeLineService.makeNetworkRequest()
//        }
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
        var tweet_list = userTimeLineService.fetchUserTimeLine()

//
//        Log.i("UserToken->",userToken)
//        Log.i("UserTokenSecret->",userTokenSecret)
//        val cb = ConfigurationBuilder().setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
//            .setOAuthConsumerSecret(TWITTER_CONSUMER_KEY_SECRET)
//            .setOAuthAccessToken(userToken)
//            .setOAuthAccessTokenSecret(userTokenSecret)
//            .setTweetModeExtended(true)
//            .build()
//        val twitter = TwitterFactory(cb).instance
//        Thread {
//            val statuses : List<Status> = twitter.getHomeTimeline()
//            for(status in statuses) {
//                var updateText : String ?= null
//                if(status.retweetedStatus != null)
//                    updateText = status.retweetedStatus.text
//                else
//                    updateText = status.text
//                val userTimelineEntity = UserTimelineEntity(updateText,status.user.screenName,status.createdAt.time,status.user.profileImageURL.toString())
//                userTimelineViewModel!!.insert(userTimelineEntity)
//            }
//        }.start()
//   }
//    fun displayTimeline(result: List<Tweet>) {
//        if(result != null) {
//            for(tweet in result) {
//                var id = tweet.id_str
//                var updateText : String ?= null
//                if(tweet.retweeted_status != null)
//                    updateText = tweet!!.retweeted_status!!.full_text
//                else
//                    updateText = tweet!!.full_text
//                var timeCreatedAt = tweet.created_at
//                var timeCreatedFormatted = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
//                var date = timeCreatedFormatted.parse(timeCreatedAt)
//                var epoch = date.time
//
//                val userTimelineEntity = UserTimelineEntity(id, updateText,tweet.user.screen_name,epoch,tweet.user.profile_image_url)
//                userTimelineViewModel!!.insert(userTimelineEntity)
//
//
//            }
//        }
//    }
    }
}

//TODO Refactoring the code properly
//TODO Using ListAdapter for RecyclerView
//TODO Using Glide
//TODO Adding activity for clicking urls
//TODO Making the timeline paginated
//TODO(1) Adding clickable to mentions and hashtags
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

