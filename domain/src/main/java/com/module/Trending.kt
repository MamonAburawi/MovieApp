package com.module

data class Trending(
    val adult: Boolean,
    val id: Int,
    val imgUrl: String,
    val name: String,
    val coverUrl: String,
    val mediaType: String,
    val releaseDate: String?,
    val title: String?,
    val rate: Double,
    val ratersCount: Int,
    val description: String,
    val genreIds: List<Int>,
)






