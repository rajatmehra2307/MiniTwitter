package com.example.rajatme.minitwitter.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(UserTimelineEntity::class) , version = 2)
abstract class UserTimelineDatabase : RoomDatabase(){
    abstract fun userTimelineDao() : UserTimelineDao
    // TODO injecting a singleton Database builder using Dagger
}