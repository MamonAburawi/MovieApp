package com.mamon.movieapp.screens.state

import com.dto.movie_detail.Genre

data class GenresState(
    var isLoading: Boolean = false,
    var data: List<Genre> = emptyList(),
    var error: String = ""
)