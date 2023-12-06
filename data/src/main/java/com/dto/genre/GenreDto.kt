package com.dto.genre

import com.dto.movie_detail.Genre
import com.google.gson.annotations.SerializedName

typealias Genres = GenreDto // response

data class GenreDto(
    @SerializedName("genres")
    val genres: List<Genre>
)