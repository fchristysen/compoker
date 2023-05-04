package org.greenfroyo.compoker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.greenfroyo.compoker.R

val fonts = FontFamily(
    Font(resId = R.font.newrodin_light, weight = FontWeight.Light),
    Font(resId = R.font.newrodin_medium, weight = FontWeight.Normal),
    Font(resId = R.font.newrodin_bold, weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    /* Other default text styles to override
    caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
    )
    */
)

