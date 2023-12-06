package com.module

import android.os.Parcelable
import com.dto.movie_detail.Genre
import com.dto.movie_detail.ProductionCompany
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val id: Int,
    val budget: Int,
    val homePage: String,
    val imdbId: String?,
    val adult: Boolean,
    val originalLanguage: String,
    val imgUrl: String,
    val coverUrl: String,
    val description: String,
    val releaseDate: String,
    val title:String,
    val rate: Double,
    val genres: List<Genre>,
    val ratesCount: Int,
    val tagLine: String,
    val revenue: Int,
    val productionCompany: List<ProductionCompany>
): Parcelable
