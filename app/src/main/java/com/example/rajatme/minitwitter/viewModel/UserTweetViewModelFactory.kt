package com.example.rajatme.minitwitter.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.services.UserhomepageService

class UserTweetViewModelFactory(private val screenName : String, private val service : UserhomepageService) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserTweetViewModel(screenName,service) as T
    }
}