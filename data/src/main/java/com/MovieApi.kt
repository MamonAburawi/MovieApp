package com

import com.dto.cast.Casts
import com.dto.genre.Genres
import com.dto.movie_detail.MovieDetailDto
import com.dto.movie_lists.Movies
import com.utils.Constants
import com.utils.TimeWindow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query



interface MovieApi {

    @GET("3/search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN
    ): Movies


    @GET("3/movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN,
    ): MovieDetailDto



    @GET("3/trending/all/{time_window}")
    suspend fun getTrendingMoves(
        @Path("time_window") timeWindow: String = TimeWindow.Day.time,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN
    ): Movies


    @GET("3/movie/top_rated")
    suspend fun getTopRatedMoves(
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = Constants.INITIAL_PAGE,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN
    ): Movies


    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMoves(
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = Constants.INITIAL_PAGE,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN
    ): Movies


    @GET("3/movie/popular")
    suspend fun getPopularMoves(
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = Constants.INITIAL_PAGE,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN
    ): Movies


    @GET("3/movie/upcoming")
    suspend fun getUpcomingMoves(
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = Constants.INITIAL_PAGE,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN
    ): Movies


    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN,
    ): Genres


    @GET("3/movie/{movie_id}/credits")
    suspend fun getCasts(
        @Path("movie_id") movieId: Int,
        @Header("accept") accept: String = Constants.APPLICATION_FILE,
        @Header("Authorization") authorization: String = Constants.TOKEN,
    ): Casts



}