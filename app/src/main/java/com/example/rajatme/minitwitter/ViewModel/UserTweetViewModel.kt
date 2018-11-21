package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.example.database.UserTimelineEntity
import com.example.network.models.Tweet
import com.example.services.UserTimeLineService
import com.example.services.UserhomepageService

class UserTweetViewModel(screenName : String) : ViewModel() {

var repository = UserhomepageService()

private val result = repository.fetchTweetsbyUser(screenName)
fun getTweetByUser() : LiveData<PagedList<Tweet>>? = result

}