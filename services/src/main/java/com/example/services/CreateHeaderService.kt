package com.example.services

import com.example.services.Utils.*
import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import kotlin.math.sign

class CreateHeaderService {
    companion object {
        var oauthVersion = "1.0"
        fun createHeader(parametersMap: MutableMap<String, String>, httpMethod: String): String {
            var oauthMap : MutableMap<String,String> = mutableMapOf<String,String>()
            oauthMap.put(OAUTH_TWITTER_CONSUMER_KEY_STRING, TWITTER_CONSUMER_KEY)
            oauthMap.put(OAUTH_TWITTER_SIGNATURE_METHOD_STRING, OAUTH_SIGNATURE_METHOD)
            oauthMap.put(OAUTH_TWITTER_TIMESTAMP_STRING, (System.currentTimeMillis() / 1000).toString())
            oauthMap.put(OAUTH_TWITTER_NONCE_STRING, OAuthUtil.getNonce())
            oauthMap.put(OAUTH_TWITTER_VERSION_STRING, oauthVersion)
            oauthMap.put(OAUTH_TWITTER_TOKEN_STRING, OAuthTokenObject.token)
            var temp = mutableMapOf<String, String>()
            for (parameter in parametersMap) {
                temp.put(OAuthUtil.encode(parameter.key), OAuthUtil.encode(parameter.value))
            }
            for(entry in oauthMap) {
                temp.put(OAuthUtil.encode(entry.key),OAuthUtil.encode(entry.value))
            }
            var sortedMap = temp.toSortedMap()
            var encoded = "";
            for (entry in sortedMap) {
                encoded += entry.key + "=" + entry.value + "&"
            }
            encoded = encoded.substring(0, encoded.length - 1)
            var url = BASE_URL + "1.1/statuses/home_timeline.json"
            var finalString = httpMethod + "&" + OAuthUtil.encode(url) + "&" + OAuthUtil.encode(encoded)
            var signature = OAuthUtil.generateSignature(
                    finalString,
                    TWITTER_CONSUMER_KEY_SECRET,
                    OAuthTokenObject.tokenSecret

            )
            signature = signature.substring(0,signature.length - 1)
            signature = OAuthUtil.encode(signature)
            oauthMap.put(OAUTH_TWITTER_SIGNATURE_STRING,signature)
            var header = "OAuth "
            for(entry in oauthMap) {
                header += entry.key + "=\"" + entry.value + "\","
            }
            header = header.substring(0,header.length-1)
            return header
        }
    }
}
