package com.dto.movie_lists

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val cover: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val description: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("media_type")
    val mediaType: String?,
    val name: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
)