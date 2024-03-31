package com.example.movieappmad24.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.widgets.SimpleTopAppBar
import com.example.movieappmad24.widgets.SimpleBottomAppBar

@Composable
fun DetailScreen(movieId: String?, navController: NavHostController) {
    val curmovie = getMovies().find { it.id == movieId }

    Scaffold(
        topBar = {
            if (curmovie != null) {
                SimpleTopAppBar(
                    title = curmovie.title,
                    navigationIcon = Icons.Filled.ArrowBack,
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            curmovie?.let { movie ->
                if (movie.images.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .height(160.dp)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 4.dp),

                    ) {
                        AsyncImage(
                            model = movie.images.first(),
                            contentDescription = "img1",
                            modifier = Modifier.height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    MovieDetails(movie = movie)

                    LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
                        items(movie.images.drop(1)) { imageUrl ->
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(250.dp, 250.dp),
                                elevation = CardDefaults.cardElevation(5.dp)
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "img",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = movie.title)
        Icon(
            modifier = Modifier
                .clickable {
                    showDetails = !showDetails
                },
            imageVector =
            if (showDetails) Icons.Filled.KeyboardArrowDown
            else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
        )
    }

    AnimatedVisibility(
        visible = showDetails,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.bodySmall)

            Divider(modifier = Modifier.padding(vertical = 3.dp))

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp)) {
                    append("Plot: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.DarkGray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(movie.plot)
                }
            })
        }
    }
}