package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.services.SearchTweetService

class SearchTweetViewModel(var api : TwitterapiService) : ViewModel() {
    var repository = SearchTweetService(api)
    fun searchTweets(query : String) : LiveData<PagedList<Tweet>>? {
        val result = repository.searchTweets(query)
        return result
    }

}