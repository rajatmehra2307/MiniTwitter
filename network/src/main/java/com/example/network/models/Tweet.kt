package com.example.network.models

import com.google.gson.annotations.SerializedName

data class Tweet(
    @SerializedName("created_at")var created_at :  String,
    @SerializedName("id_str")var id : String,
    var full_text: String,
    var user : TweetUser,
    val retweeted_status : Tweet ?= null
)

data class TweetUser(
    var id_str : String,
    var screen_name : String,
    var description : String,
    var followers_count : Long,
    var friends_count : Long,
    var profile_image_url : String
)