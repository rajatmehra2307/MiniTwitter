package com.example.rajatme.minitwitter.ViewModel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.content.Context
import com.example.database.UserTimelineEntity
import com.example.services.UserTimeLineService

class UserTimelineViewModel(context : Context) : ViewModel() {
    var repository = UserTimeLineService.create(context)

    private val result = repository.fetchUserTimeLine()
    fun getTimeLine() : LiveData<PagedList<UserTimelineEntity>>? {
        return result
    }

}