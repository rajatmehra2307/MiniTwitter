package com.example.model

import android.arch.paging.PagedList
import com.example.database.TimelineCache
import com.example.database.UserTimelineEntity
import com.example.network.TwitterapiService

class TimelineBoundaryCallback(private val cache : TimelineCache) : PagedList.BoundaryCallback<UserTimelineEntity>() {
    private var isRequestInProgress = false
    val apiservice by lazy {
        TwitterapiService.create()
    }

    override fun onZeroItemsLoaded() {

    }

    override fun onItemAtEndLoaded(itemAtEnd: UserTimelineEntity) {
    }

    fun requestAndSave(authorizationHeader : String, shouldIncludeRetweet: Boolean, count: Long, shouldIncludeEntities: Boolean, tweetMode : String) {
        if(isRequestInProgress)
            return
        val tweet_list = apiservice.getTimeline(apiservice,authorizationHeader,shouldIncludeRetweet,count,shouldIncludeEntities, tweetMode, {
            cache.insert(it)
        })
        isRequestInProgress = false

    }
}