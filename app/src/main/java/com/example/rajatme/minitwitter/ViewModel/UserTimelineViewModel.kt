package com.example.rajatme.minitwitter.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.rajatme.minitwitter.Database.UserTimelineEntity
import com.example.rajatme.minitwitter.Repository.TimelineRepository

class UserTimelineViewModel(application: Application) : AndroidViewModel(application) {
    private val timelineRepository = TimelineRepository(application)
    fun insert(userTimelineEntity: UserTimelineEntity) {
        timelineRepository.insert(userTimelineEntity)
    }
    fun getTimeline() : LiveData<List<UserTimelineEntity>> {
        return timelineRepository.timeline
    }
}