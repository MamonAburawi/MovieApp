package com.mamon.movieapp.screens.state


import com.module.MovieDetail

data class MovieDetailState(
    var isLoading: Boolean = false,
    var data: MovieDetail? = null,
    var error: String = ""
)
