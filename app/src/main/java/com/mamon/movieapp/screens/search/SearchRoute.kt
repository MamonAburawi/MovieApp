package com.mamon.movieapp.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mamon.movieapp.screens.movie_detail.MovieDetails
import okhttp3.Route

private const val ROUTE = "Search"


fun NavController.navigateToSearch(){
    navigate(ROUTE)
}


fun NavGraphBuilder.searchRoute(navController: NavController) {
    composable(route = ROUTE) { Search(navController = navController) }
}

class SearchArgs(savedStateHandle: SavedStateHandle) {

}