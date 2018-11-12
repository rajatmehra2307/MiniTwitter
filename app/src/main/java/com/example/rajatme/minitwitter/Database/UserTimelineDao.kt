package com.example.rajatme.minitwitter.Database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface UserTimelineDao {

    @Query("SELECT * FROM userTimeline")
    fun getUserTimeline() : List<UserTimelineEntity>

    @Insert
    fun insert(vararg userTimelineEntity: UserTimelineEntity)

}