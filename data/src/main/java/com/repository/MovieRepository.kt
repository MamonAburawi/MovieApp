package com.repository

import com.dto.cast.Casts
import com.dto.genre.Genres
import com.dto.movie_detail.MovieDetailDto
import com.dto.movie_lists.Movies

interface MovieRepository {

    suspend fun searchByName(query: String): Movies
    suspend fun getTrendingMovies(time: String): Movies
    suspend fun getMovieById(movieId: Int): MovieDetailDto
    suspend fun getTopRatedMovies(page: Int): Movies
    suspend fun getUpcomingMovies(page: Int): Movies
    suspend fun getPopularMovies(page: Int): Movies
    suspend fun getNowPlayingMovies(page: Int): Movies
    suspend fun getGenres(): Genres
    suspend fun getCasts(movieId: Int): Casts

}

