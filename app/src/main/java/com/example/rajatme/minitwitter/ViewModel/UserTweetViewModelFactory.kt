package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class UserTweetViewModelFactory(private val screenName : String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserTweetViewModel(screenName) as T
    }
}