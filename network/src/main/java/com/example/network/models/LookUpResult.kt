package com.example.network.models

import com.google.gson.annotations.SerializedName

data class LookUpResult(
    @SerializedName("name") var userName : String,
    @SerializedName("screen_name") var userHandle: String,
    @SerializedName("connections") var connection : List<String>
)