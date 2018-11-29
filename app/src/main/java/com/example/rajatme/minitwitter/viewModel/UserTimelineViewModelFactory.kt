package com.example.rajatme.minitwitter.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.services.UserTimeLineService

class UserTimelineViewModelFactory(private val userTimeLineService: UserTimeLineService) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserTimelineViewModel(userTimeLineService) as T
    }
}
