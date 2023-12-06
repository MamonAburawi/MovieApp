package com.dto.movie_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int = 0,
    val name: String = ""
): Parcelable