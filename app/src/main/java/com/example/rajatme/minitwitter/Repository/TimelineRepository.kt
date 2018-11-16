package com.example.rajatme.minitwitter.Repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import com.example.rajatme.minitwitter.Database.UserTimelineDao
import com.example.rajatme.minitwitter.Database.UserTimelineDatabase
import com.example.rajatme.minitwitter.Database.UserTimelineEntity

class TimelineRepository(application: Application){
    val instance = Room.databaseBuilder(application.applicationContext,UserTimelineDatabase::class.java,"Timeline_database").build()
    var dao : UserTimelineDao = instance.userTimelineDao()
    var timeline : LiveData<List<UserTimelineEntity>> = dao.getUserTimeline()

    fun insert(userTimelineEntity: UserTimelineEntity) {
        insertAsyncTask(dao).execute(userTimelineEntity)
    }

    class insertAsyncTask(var dao : UserTimelineDao?) : AsyncTask<UserTimelineEntity, Void, Unit>() {
        override fun doInBackground(vararg params: UserTimelineEntity): Unit {
           dao!!.insert(params[0])
        }
    }

}
