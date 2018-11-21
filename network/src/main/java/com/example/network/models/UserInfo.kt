package com.example.network.models

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id_str") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("screen_name") var handle : String,
    @SerializedName("description") var description: String,
    @SerializedName("followers_count") var followers : Long,
    @SerializedName("friends_count") var friends : Long,
    @SerializedName("profile_image_url") var imageUrl : String
)
