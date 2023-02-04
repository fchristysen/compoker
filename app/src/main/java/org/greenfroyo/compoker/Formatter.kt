package org.greenfroyo.compoker

import java.text.DecimalFormat

inline fun Int.toCredit(): String{
    val decimalFormatter = DecimalFormat("#,###")
    return "$${decimalFormatter.format(this)}"
}