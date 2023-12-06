package com.mamon.movieapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mamon.movieapp.screens.home.homeRoute
import com.mamon.movieapp.screens.movie_detail.movieDetailRoute
import com.mamon.movieapp.screens.search.searchRoute

@Composable
fun MovieHostGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {

        homeRoute(navController)
        movieDetailRoute(navController)
        searchRoute(navController)
    }
}