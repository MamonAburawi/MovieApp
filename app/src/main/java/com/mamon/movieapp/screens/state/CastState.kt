package com.mamon.movieapp.screens.state

import com.module.Cast

class CastState(
    var isLoading: Boolean = false,
    var data: List<Cast> = emptyList(),
    var error: String = ""
)