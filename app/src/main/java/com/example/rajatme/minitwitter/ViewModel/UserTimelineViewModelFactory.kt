package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class UserTimelineViewModelFactory(private val context : Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserTimelineViewModel(context) as T
    }
}
