package com.example.rajatme.minitwitter.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.network.TwitterapiService

class SearchTweetViewModelFactory(private val api: TwitterapiService) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchTweetViewModel(api) as T
    }
}