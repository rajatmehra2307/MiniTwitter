package com.example.network.models

data class Tweet(
    var created_at :  String,
    var id_str : String,
    var full_text: String,
//   var entities : TweetEntity,
// var extended_entities : TweetExtendedEntity,
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

//class TweetExtendedEntity {
//
//}

//class TweetEntity {
//
//}

//data class TwitterTimelineResult(val result : List<Tweet>)