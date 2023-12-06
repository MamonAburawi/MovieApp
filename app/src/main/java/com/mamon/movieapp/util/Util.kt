package com.mamon.movieapp.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import com.utils.MediaType


fun trimTitle(text: String, textLength: Int = 26) = if (text.length <= textLength) text else {
    val textWithEllipsis = text.removeRange(startIndex = textLength, endIndex = text.length)
    "$textWithEllipsis..."
}

val homeMenuItems = listOf(MediaType.Movie.type, MediaType.Tv.type)

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }


//fun <A> String.fromJson(type: Class<A>): A {
//    return Gson().fromJson(this, type)
//}
//
//fun <A> A.toJson(): String? {
//    return Gson().toJson(this)
//}
