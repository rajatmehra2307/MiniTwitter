package com.example.network.models

import com.google.gson.annotations.SerializedName

data class FollowersList(
    @SerializedName("users") var follwerList : List<UserInfo>,
    @SerializedName("next_cursor_str") var nextCursor : String,
    @SerializedName("previous_cursor_str") var prevCursor : String
)
