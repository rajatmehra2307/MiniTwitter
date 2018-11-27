package com.example.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.persistence.room.Room
import android.content.Context
import com.example.database.TimelineCache
import com.example.database.UserTimelineDatabase
import com.example.database.UserTimelineEntity
import com.example.network.TwitterapiService
import com.example.services.model.TimelineBoundaryCallback
import java.util.concurrent.Executors
import javax.inject.Inject

class UserTimeLineService @Inject constructor(private val cache : TimelineCache, var api : TwitterapiService) {

    private val boundaryCallback = TimelineBoundaryCallback(cache,api)
    private val DATABASE_PAGE_SIZE = 20
    fun fetchUserTimeLine() : LiveData<PagedList<UserTimelineEntity>>? {
        val dataSourceFactory = cache.getTimelineFromCache()
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE).setBoundaryCallback(boundaryCallback).build()
        return data
    }
    fun makeNetworkRequest() {
        boundaryCallback.requestAndSave(null,null)
    }
//    companion object {
//        fun create(context : Context) : UserTimeLineService {
//            val database = Room.databaseBuilder(
//                context.applicationContext,
//                UserTimelineDatabase::class.java, "UserTimeline_database"
//            ).build()
//            return UserTimeLineService(TimelineCache(database.userTimelineDao(), Executors.newSingleThreadExecutor()),
//                TwitterapiService.create())
//        }
//
//    }


}