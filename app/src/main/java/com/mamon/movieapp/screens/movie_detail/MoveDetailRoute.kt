package com.mamon.movieapp.screens.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mamon.movieapp.screens.movie_detail.MovieDetailArgs.Companion.KEY_MOVIE_ID
import com.module.Movie
import com.utils.fromJson

private const val ROUTE = "MovieDetail"


fun NavController.navigateToMovieDetail(moveId: Int){
    navigate("${ROUTE}/$moveId")
}


fun NavGraphBuilder.movieDetailRoute(navController: NavController) {
    composable(

        route = "$ROUTE/{${KEY_MOVIE_ID}}",
        arguments = listOf(
            navArgument(KEY_MOVIE_ID) { type = NavType.IntType },
        )
    ) { MovieDetails(navController = navController) }
}

class MovieDetailArgs(savedStateHandle: SavedStateHandle) {

    val movieId: Int = checkNotNull(savedStateHandle[KEY_MOVIE_ID])
//    private val movieJson: String = checkNotNull(savedStateHandle[KEY_MOVIE_ID])
//    private val movie = movieJson.fromJson(Movie::class.java)
//    val movieId: Int = movie.id
//    val genresId: List<Int> = movie.genreIds


    companion object {
        const val KEY_MOVIE_ID: String = "MovieId"
        const val KEY_GENRES_ID: String = "Genres"
    }

}