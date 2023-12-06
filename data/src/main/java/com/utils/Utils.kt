package com.utils

import com.dto.movie_detail.Genre

fun createImgUrl(url: String?) =if(url != null) "https://image.tmdb.org/t/p/w500/${url}" else ""

sealed class TimeWindow(val time: String){
    object Day: TimeWindow("day")
    object Week: TimeWindow("week")
}


sealed class MediaType(val type: String){
    object Movie: MediaType("movie")
    object Tv: MediaType("tv")
}




fun getGenresById(genres: List<Genre>?, genresIds: List<Int>): List<Genre> {
    return genres
        ?.filter { it.id in genresIds }
        ?.map { it } ?: emptyList()
}

