package com.mamon.movieapp.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val ROUTE = "Home"

fun NavController.navigateToHome(){
    navigate(ROUTE)
}

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable(ROUTE) { Home(navController) }
}

class CoinDetailArgs(savedStateHandle: SavedStateHandle) {
    companion object {

    }
}