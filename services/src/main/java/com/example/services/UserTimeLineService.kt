package com.example.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.database.TimelineCache
import com.example.database.UserTimelineDatabase
import com.example.database.UserTimelineEntity
import com.example.network.TwitterapiService

import com.example.network.models.Tweet
import com.example.services.Utils.BASE_URL
import com.example.services.model.TimelineBoundaryCallback
import io.reactivex.disposables.Disposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executors

class UserTimeLineService(private val cache : TimelineCache) {

    private val boundaryCallback = TimelineBoundaryCallback(cache)
    fun fetchUserTimeLine() : LiveData<PagedList<UserTimelineEntity>>? {
        val dataSource = cache.getTimelineFromCache()
        val data = LivePagedListBuilder(dataSource, DATABASE_PAGE_SIZE).setBoundaryCallback(boundaryCallback).build()
        return data
    }
    fun makeNetworkRequest() {
        boundaryCallback.requestAndSave(null,null)
    }
    companion object {
        fun create(context : Context) : UserTimeLineService {
            val database = Room.databaseBuilder(
                context.applicationContext,
                UserTimelineDatabase::class.java, "UserTimeline_database"
            ).build()
            return UserTimeLineService(TimelineCache(database.userTimelineDao(), Executors.newSingleThreadExecutor()))
        }

        private const val DATABASE_PAGE_SIZE = 20
    }




}