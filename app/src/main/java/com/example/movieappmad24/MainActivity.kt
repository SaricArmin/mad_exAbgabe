package com.example.movieappmad24
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.LightColorScheme
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LightColorScheme.primary
                ) {
                    TopAppNavigationBar()
                }
            }
        }
    }
}

//TopBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppNavigationBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightColorScheme.primaryContainer,
                    titleContentColor = LightColorScheme.primary,
                ),
                title = {
                    Text("Movie App", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
            )
        },
        bottomBar = {
            BottomAppNavigationBar()
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MovieList(movies = getMovies())
        }
    }
}

@Composable
fun BottomAppNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Watchlist") },
            selected = false,
            onClick = { }
        )
    }
}

@Composable
fun MovieList(movies: List<Movie> = getMovies()){
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie)
        }
    }
}


@Composable
fun MovieRow(movie: Movie){
    var showDetails by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 5.dp)
            .animateContentSize(),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.images.first()),
                    contentDescription = "img",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    Icon(
                        tint = MaterialTheme.colorScheme.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp)
                )
                Icon(
                    modifier = Modifier
                        .clickable { showDetails = !showDetails }
                        .padding(end = 8.dp),
                    imageVector = if (showDetails) Icons.Filled.KeyboardArrowDown
                    else Icons.Filled.KeyboardArrowUp,
                    contentDescription = if (showDetails) "less" else "more"
                )
            }
            AnimatedVisibility(visible = showDetails) {
                Column(modifier = Modifier.padding(start = 12.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)) {
                    MovieInfo(movie)
                }
            }
        }
    }
}

//only refactored this for now
@Composable
fun MovieInfo(movie: Movie) {
    Text("Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
    Text("Year: ${movie.year}", style = MaterialTheme.typography.bodySmall)
    Text("Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
    Text("Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
    Text("Rating: ${movie.rating}", style = MaterialTheme.typography.bodySmall)
    Box(modifier = Modifier.padding(vertical = 4.dp)) {
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(350.dp)
        )
    }
    Text("Plot: ${movie.plot}", style = MaterialTheme.typography.bodySmall)
}


//@Preview
//@Composable
//fun DefaultPreview(){
//    MovieAppMAD24Theme {
//        MovieList(movies = getMovies())
//    }
//}
