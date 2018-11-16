package com.example.rajatme.minitwitter.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface UserTimelineDao {

    @Query("SELECT * FROM userTimeline")
    fun getUserTimeline() : LiveData<List<UserTimelineEntity>>

    @Insert
    fun insert(vararg userTimelineEntity: UserTimelineEntity)

    @Insert
    fun insertAll(userTimelineEntities: List<UserTimelineEntity>)

}