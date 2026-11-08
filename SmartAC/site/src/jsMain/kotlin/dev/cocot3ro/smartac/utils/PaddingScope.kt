package dev.cocot3ro.smartac.utils

import com.varabyte.kobweb.compose.css.CSSLengthOrPercentageNumericValue
import com.varabyte.kobweb.compose.ui.modifiers.PaddingScope
import com.varabyte.kobweb.compose.ui.modifiers.leftRight
import com.varabyte.kobweb.compose.ui.modifiers.topBottom

fun PaddingScope.all(value: CSSLengthOrPercentageNumericValue) {
    topBottom(value)
    leftRight(value)
}