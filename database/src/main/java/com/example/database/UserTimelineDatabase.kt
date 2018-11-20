package com.example.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(UserTimelineEntity::class),
          version = 2)
abstract class UserTimelineDatabase : RoomDatabase(){
    abstract fun userTimelineDao() : UserTimelineDao
    companion object {

        @Volatile
        private var INSTANCE: UserTimelineDatabase? = null
        fun getInstance(context: Context): UserTimelineDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UserTimelineDatabase::class.java, "Usertimeline_database")
                .build()
    }

}