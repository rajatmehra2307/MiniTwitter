package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.database.UserTimelineEntity
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.TwitterApplication
//import com.example.network.service.UserTimeLineService
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModel
import com.example.rajatme.minitwitter.ViewModel.UserTimelineViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserTimelineAdapter
import com.example.rajatme.minitwitter.adapters.UserTweetAdapter
import com.example.rajatme.minitwitter.databinding.ActivityTimelineBinding
import com.example.rajatme.minitwitter.databinding.SearchTweetBinding
import com.example.services.UserTimeLineService
import com.example.services.Utils.OAuthTokenObject
import com.example.services.Utils.PREFERENCE_NAME
import com.example.services.Utils.PREF_KEY_OAUTH_SECRET
import com.example.services.Utils.PREF_KEY_OAUTH_TOKEN
import javax.inject.Inject

class TimeLineFragment : Fragment() {

    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter = UserTimelineAdapter()

    @Inject
    lateinit var userTimelineViewModel: UserTimelineViewModel

    var swipeRefreshlayout : SwipeRefreshLayout ?= null

    @Inject
    lateinit var  userTimeLineService : UserTimeLineService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       (this.activity?.application as TwitterApplication).component!!.inject(this)
        val binding : ActivityTimelineBinding = DataBindingUtil.inflate(inflater,R.layout.activity_timeline,container,false)
        val rootView = binding.root
        recyclerView = binding.recyclerView
        userTimelineViewModel =
                ViewModelProviders.of(this, UserTimelineViewModelFactory(userTimeLineService)).get(UserTimelineViewModel::class.java)
        val observer = Observer<PagedList<UserTimelineEntity>> {
            recyclerViewAdapter.submitList(it)
        }
        userTimelineViewModel!!.getTimeLine()!!.observe(this, observer)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(activity!!.baseContext)
        swipeRefreshlayout = binding.swipeRefresh
        swipeRefreshlayout?.setOnRefreshListener{
            userTimelineViewModel.makeNetworkRequest()
            swipeRefreshlayout?.isRefreshing = false
        }
        fetchTimeline()
        return rootView
    }

    fun fetchTimeline() {
        val sharedPreferences = this.activity!!.getSharedPreferences(PREFERENCE_NAME, 0)
        val userToken = sharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, null)
        val userTokenSecret = sharedPreferences.getString(PREF_KEY_OAUTH_SECRET, null)
        val oAuthTokenObject = OAuthTokenObject(userToken, userTokenSecret)


    }
}

