package com.utils

import com.dto.cast.CastDto
import com.dto.movie_detail.MovieDetailDto
import com.dto.movie_lists.MovieDto
import com.module.Cast
import com.module.Movie
import com.module.MovieDetail


fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title ?: "",
        releaseDate = releaseDate ?: "",
        rate = voteAverage,
        ratersCount = voteCount,
        imgUrl = createImgUrl(posterPath),
        coverUrl = createImgUrl(cover),
        description = description ?: "",
        genreIds = genreIds,
        name = name ?: "",
        mediaType = mediaType ?: ""
    )
}


fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        budget = budget,
        homePage = homepage,
        imdbId = imdbId,
        adult = adult,
        originalLanguage = originalLanguage,
        imgUrl = createImgUrl(posterPath),
        coverUrl = createImgUrl(backdropPath),
        description = overview,
        releaseDate = releaseDate,
        title = title,
        rate = voteAverage,
        ratesCount = voteCount,
        tagLine = tagline,
        revenue = revenue,
        productionCompany = productionCompanies,
        genres = genres
    )
}

fun CastDto.toCast(): Cast{
    return Cast(
        castId = castId,
        id = id,
        name = name,
        imgUrl =  createImgUrl(profilePath),
        department = knownForDepartment
    )
}