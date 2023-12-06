package com.mamon.movieapp.screens.state

import com.module.Movie

class MovieState(
    var isLoading: Boolean = false,
    var data: List<Movie> = emptyList(),
    var error: String = ""
)