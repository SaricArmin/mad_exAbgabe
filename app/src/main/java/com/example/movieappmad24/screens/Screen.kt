package com.example.movieappmad24.screens

sealed class Screen(val route: String) {
    object HomeScreen : Screen("homescreen")
    object WatchlistScreen : Screen("watchlistscreen")
    object DetailScreen : Screen("detailscreen/{movieId}")
    { fun createRoute(movieId: String) = "detailscreen/$movieId" }
}
