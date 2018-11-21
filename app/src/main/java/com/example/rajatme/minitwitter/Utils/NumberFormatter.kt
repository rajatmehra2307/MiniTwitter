package com.example.rajatme.minitwitter.Utils

import java.text.DecimalFormat

fun formatNumber(number : Long) : String{
    var decimalFormat = DecimalFormat("0.#")
    var result : String = decimalFormat.format(number)
    if(number >= 100000000000) {
        return decimalFormat.format(number/10000000000.0) + 'G'
    }
    if(number >= 1000000) {
        return decimalFormat.format(number/1000000.0) + 'M'
    }
    if(number >= 1000)
        return decimalFormat.format(number/1000.0) + 'K'
    return result
}