package com.repository


import com.MovieApi
import com.dto.cast.Casts
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
): MovieRepository {

    override suspend fun searchByName(query: String) = api.search(query)
    override suspend fun getTrendingMovies(time: String) = api.getTrendingMoves(timeWindow = time)
    override suspend fun getMovieById(movieId: Int) = api.getMovieById(movieId)
    override suspend fun getTopRatedMovies(page: Int) = api.getTopRatedMoves(page = page)
    override suspend fun getUpcomingMovies(page: Int) = api.getUpcomingMoves(page = page)
    override suspend fun getPopularMovies(page: Int) = api.getPopularMoves(page = page)
    override suspend fun getNowPlayingMovies(page: Int) = api.getNowPlayingMoves(page = page)
    override suspend fun getGenres() = api.getMovieGenres()
    override suspend fun getCasts(movieId: Int) = api.getCasts(movieId)


}