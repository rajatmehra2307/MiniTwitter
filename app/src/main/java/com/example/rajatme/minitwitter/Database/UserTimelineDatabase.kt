package com.example.rajatme.minitwitter.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(UserTimelineEntity::class) , version = 1)
abstract class UserTimelineDatabase : RoomDatabase(){
    abstract fun userTimelineDao() : UserTimelineDao
}