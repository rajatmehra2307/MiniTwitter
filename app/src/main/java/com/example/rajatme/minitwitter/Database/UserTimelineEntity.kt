package com.example.rajatme.minitwitter.Database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userTimeline")
data class UserTimelineEntity(
    @ColumnInfo (name = "update_text") var updateText : String?,
    @ColumnInfo (name = "user_name") var userName : String?,
    @ColumnInfo (name = "time") var timeCreated : Long?,
    @ColumnInfo (name = "image_url") var imageUrl : String
) {
    @PrimaryKey (autoGenerate = true)
    var id: Long = 0
}
