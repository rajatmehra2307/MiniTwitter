package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.network.TwitterapiService
import com.example.services.UserhomepageService
import twitter4j.Twitter

class SearchTweetViewModelFactory(private val api: TwitterapiService) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchTweetViewModel(api) as T
    }
}