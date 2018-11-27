package com.example.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.services.model.SearchTweetDataSource
import com.example.services.model.SearchTweetDataSourceFactory
import javax.inject.Inject

class SearchTweetService @Inject constructor(var twitterapiService: TwitterapiService) {
    fun searchTweets(query : String) : LiveData<PagedList<Tweet>> {
        var dataSourceFactory = SearchTweetDataSourceFactory(SearchTweetDataSource(query, twitterapiService))
        var result = LivePagedListBuilder(dataSourceFactory,20).build()
        return result
    }
}