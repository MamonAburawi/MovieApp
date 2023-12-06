package com.dto.cast

typealias Casts = Credits

data class Credits(
    val cast: List<CastDto>,
    val crew: List<Crew>,
    val id: Int
)