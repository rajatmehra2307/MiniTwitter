package com.example.network.models

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("statuses") var tweetList : List<Tweet>

)