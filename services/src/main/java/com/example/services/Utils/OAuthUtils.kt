package com.example.services.Utils

import java.net.URLEncoder
import android.util.Base64
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * percentage encoding
 *
 * @return A encoded string
 */

object OAuthUtil {
    val RAND = SecureRandom()
    fun encode(value: String): String {

        var encoded = ""
        try {
            encoded = URLEncoder.encode(value, "UTF-8")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var sb = ""
        var focus: Char
        var i = 0
        while (i < encoded.length) {
            focus = encoded[i]
            if (focus == '*') {
                sb += "%2A"
            } else if (focus == '+') {
                sb += "%20"
            } else if (focus == '%' && i + 1 < encoded.length
                && encoded[i + 1] == '7' && encoded[i + 2] == 'E'
            ) {
                sb += '~'.toString()
                i += 2
            } else {
                sb += focus
            }
            i++
        }
        return sb
    }

    fun generateSignature(signatueBaseStr: String, oAuthConsumerSecret: String, oAuthTokenSecret: String?): String {
        var byteHMAC: ByteArray? = null
        try {
            val mac = Mac.getInstance("HmacSHA1")
            val spec: SecretKeySpec
            if (null == oAuthTokenSecret) {
                val signingKey = encode(oAuthConsumerSecret) + '&'
                spec = SecretKeySpec(signingKey.toByteArray(), "HmacSHA1")
            } else {
                val signingKey = encode(oAuthConsumerSecret) + '&'.toString() + encode(oAuthTokenSecret)
                spec = SecretKeySpec(signingKey.toByteArray(), "HmacSHA1")
            }
            mac.init(spec)
            byteHMAC = mac.doFinal(signatueBaseStr.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Base64.encodeToString(byteHMAC,Base64.DEFAULT)
    }

    fun getNonce(): String {
        return System.nanoTime().toString() + (Math.abs(RAND.nextLong())).toString()
    }
}