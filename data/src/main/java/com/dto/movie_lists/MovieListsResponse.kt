package com.dto.movie_lists

import com.google.gson.annotations.SerializedName

typealias Movies = MovieListsResponse // response

data class MovieListsResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
){

//    fun mapFromListModel(): List<MovieDto> {
//        return results.map { movieDto ->
//            MovieDto(
//              id = movieDto.id,
//                adult = movieDto.adult,
//                backdropPath = movieDto.backdropPath,
//                genreIds =  movieDto.genreIds,
//                originalLanguage = movieDto.originalLanguage,
//                originalTitle = movieDto.originalTitle,
//                overview = movieDto.originalTitle,
//                popularity = movieDto.popularity,
//                posterPath = movieDto.posterPath,
//                releaseDate = movieDto.releaseDate,
//                title =  movieDto.title,
//                video = movieDto.video,
//                voteAverage = movieDto.voteAverage,
//                voteCount = movieDto.voteCount,
//                mediaType = movieDto.mediaType
//            )
//        }
//    }
}