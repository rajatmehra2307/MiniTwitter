package com.example.database

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.provider.ContactsContract
import android.util.Log
import java.util.concurrent.Executor

class TimelineCache(private val userTimelineDao: UserTimelineDao,
                    private val ioExecutor: Executor) {
    fun insert(timeline : MutableList<UserTimelineEntity>?, insertfinished : () -> Unit) {
        ioExecutor.execute {
            Log.d("UserTimelineLocal cache", "inserting ${timeline?.size} entries")
            userTimelineDao.insertAll(timeline)
            insertfinished()
        }
    }

    fun getTimelineFromCache() : DataSource.Factory<Int,UserTimelineEntity> {
        return userTimelineDao.getUserTimeline()
    }
}