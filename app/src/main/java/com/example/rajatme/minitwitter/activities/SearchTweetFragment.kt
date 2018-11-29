package com.example.rajatme.minitwitter.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.TwitterApplication
import com.example.rajatme.minitwitter.viewModel.SearchTweetViewModel
import com.example.rajatme.minitwitter.viewModel.SearchTweetViewModelFactory
import com.example.rajatme.minitwitter.adapters.UserTweetAdapter
import com.example.rajatme.minitwitter.databinding.SearchTweetBinding
import javax.inject.Inject

class SearchTweetFragment :Fragment() {
    private var searchView : SearchView?= null
    private var recyclerView : RecyclerView ?= null
    private var fragment : Fragment = this

    @Inject
    lateinit var api : TwitterapiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : SearchTweetBinding = DataBindingUtil.inflate(inflater,R.layout.activity_search_tweet,container,false)
        var rootView = binding.root
        searchView = binding.searchView
        recyclerView = binding.recyclerViewTweet
        (this.activity?.application as TwitterApplication).component!!.inject(this)
        var recyclerViewAdapter = UserTweetAdapter()
        var searchTweetViewModel =
            ViewModelProviders.of(this, SearchTweetViewModelFactory(api)).get(SearchTweetViewModel::class.java)
        val observer = Observer<PagedList<Tweet>> {
            recyclerViewAdapter.submitList(it)
        }

        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this.activity)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchTweetViewModel!!.searchTweets(query!!)?.observe(fragment,observer)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
        return rootView
    }
}
