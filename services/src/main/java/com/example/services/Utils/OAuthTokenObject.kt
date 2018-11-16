package com.example.services.Utils

class OAuthTokenObject(param1 : String, param2: String) {
    init {
        token = param1
        tokenSecret = param2
    }
    companion object {
        lateinit var token: String
        lateinit var tokenSecret : String

    }

}
