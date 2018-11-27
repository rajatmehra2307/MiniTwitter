package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.services.UserhomepageService

class UserTweetViewModelFactory(private val screenName : String, private val service : UserhomepageService) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserTweetViewModel(screenName,service) as T
    }
}