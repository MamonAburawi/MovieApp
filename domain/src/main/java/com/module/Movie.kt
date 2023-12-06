package com.module

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.utils.toJson
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val id: Int = 0,
    val imgUrl: String = "",
    val coverUrl: String = "",
    val releaseDate: String? = "",
    val title: String? = "",
    val rate: Double = 0.0,
    val ratersCount: Int = 0,
    val description: String? = "",
    val genreIds: List<Int> = emptyList(),
    val name: String? = "",
    val mediaType: String? = ""
): Parcelable
