package com.example.rajatme.minitwitter.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.database.UserTimelineDao
import com.example.database.UserTimelineDatabase
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = arrayOf(ContextModule::class))
class DatabaseModule {
    @Singleton
    @Provides
    fun getDatabase(context : Context) : UserTimelineDatabase {
        return Room.databaseBuilder(context,UserTimelineDatabase::class.java,"Usertimeline_Database")
            .build()
    }

    @Singleton
    @Provides
    fun getDao(db : UserTimelineDatabase) : UserTimelineDao{
        return db.userTimelineDao()
    }

    @Singleton
    @Provides
    fun getExecutor() : Executor {
        return Executors.newSingleThreadExecutor()
    }
}