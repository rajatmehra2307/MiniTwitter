package com.example.database

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface UserTimelineDao {

    @Query("SELECT * FROM userTimeline ORDER BY id DESC")
    fun getUserTimeline() : DataSource.Factory<Int, UserTimelineEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg userTimelineEntity: UserTimelineEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(userTimelineEntities: MutableList<UserTimelineEntity>?)

}