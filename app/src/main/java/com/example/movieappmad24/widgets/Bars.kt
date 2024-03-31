package com.example.movieappmad24.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.screens.Screen
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = if (navigationIcon != null && onNavigationIconClick != null) {
            {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "Navigation Icon"
                    )
                }
            }
        } else {
            {}
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun SimpleBottomAppBar(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == Screen.HomeScreen.route,
            onClick = { navController.navigate(Screen.HomeScreen.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Watchlist") },
            label = { Text("Watchlist") },
            selected = currentRoute == Screen.WatchlistScreen.route,
            onClick = { navController.navigate(Screen.WatchlistScreen.route) }
        )
    }
}
