package com.example.rajatme.minitwitter.viewModel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.example.database.UserTimelineEntity
import com.example.services.UserTimeLineService
import javax.inject.Inject

class UserTimelineViewModel @Inject constructor(var userTimeLineService: UserTimeLineService) : ViewModel() {
    var repository = userTimeLineService

    fun getTimeLine() : LiveData<PagedList<UserTimelineEntity>>? {
        return repository.fetchUserTimeLine()
    }

    fun makeNetworkRequest() {
        repository.makeNetworkRequest()
    }



}