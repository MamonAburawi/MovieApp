package com.mamon.movieapp.screens.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dto.movie_detail.Genre
import com.mamon.movieapp.R
import com.mamon.movieapp.component.*
import com.mamon.movieapp.screens.state.GenresState
import com.mamon.movieapp.screens.state.MovieState
import com.mamon.movieapp.screens.movie_detail.navigateToMovieDetail
import com.mamon.movieapp.screens.search.navigateToSearch
import com.mamon.movieapp.ui.theme.*
import com.mamon.movieapp.util.homeMenuItems
import com.module.Movie
import com.utils.Constants

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val stateDetail = viewModel.stateMovieDetail.collectAsState().value
    val topRatedState = viewModel.stateTopRating.collectAsState().value
    val upComingState = viewModel.stateUpComing.collectAsState().value
    val nowPlayingState = viewModel.stateNowPlaying.collectAsState().value
    val popularState = viewModel.statePopular.collectAsState().value
    val trendingState = viewModel.stateTrending.collectAsState().value
    val genresState = viewModel.stateGenres.collectAsState().value

    val currentPage = viewModel.page.collectAsState().value
    val selectedGenre = viewModel.genre.collectAsState().value


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {


        HomeContent(
            trendingState = trendingState,
            genresState = genresState,
            topRatedState = topRatedState,
            upComingState = upComingState,
            nowPlayingState = nowPlayingState,
            popularState = popularState,
            onSearchClicked = {
                navController.navigateToSearch()
            },
            onGenreItemClicked = { genre ->
                viewModel.setGenre(genre)
            },
            onMenuItemSelected = { type ->
                viewModel.setMediaType(type)
            },
            onMovieItemClicked = { movie ->
                navController.navigateToMovieDetail(moveId = movie.id)
            },
            onPreviewsClicked = {
                viewModel.previewsPage()
            },
            onNextClicked = {
                viewModel.nextPage()
            },
            currentPage = currentPage,
            totalPages = Constants.TOTAL_PAGES,
            selectedGenre = selectedGenre,
            onError = {
                OnError(
                    error = it,
                    onRefreshClicked = {
                        viewModel.refresh()
                    }
                )
            },
            onLoading = {
                CircularProgressIndicator()
            }
        )



    }

}



@Composable
fun HomeContent(
    genresState: GenresState = GenresState(),
    trendingState: MovieState = MovieState(),
    topRatedState: MovieState = MovieState(),
    popularState: MovieState = MovieState(),
    upComingState: MovieState = MovieState(),
    nowPlayingState: MovieState = MovieState(),
    onSearchClicked: () -> Unit = {},
    onMovieItemClicked: (Movie) -> Unit = {},
    onGenreItemClicked: (Genre) -> Unit = {},
    onMenuItemSelected: (String) -> Unit,
    currentPage: Int,
    totalPages: Int,
    onLoading: @Composable () -> Unit,
    onError: @Composable (String) -> Unit,
    selectedGenre: Genre,
    onNextClicked:()-> Unit,
    onPreviewsClicked:()-> Unit,
) {
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue),
    contentAlignment = Center
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Spacer(modifier = Modifier.heightIn(20.dp))

        // top app bar
        HomeAppBar(
            onProfileClicked = {},
            onSearchClicked = onSearchClicked
        )

        // list
        MovieCollection (
            trendingState = trendingState,
            genresState = genresState,
            topRatedState = topRatedState,
            popularState = popularState,
            upComingState = upComingState,
            nowPlayingState = nowPlayingState,
            onMovieItemClicked = { onMovieItemClicked(it) },
            onGenreItemClicked = { onGenreItemClicked(it) },
            onMenuItemSelected = { onMenuItemSelected(it)},
            onNextClicked = onNextClicked,
            onPreviewsClicked = onPreviewsClicked,
            currentPage = currentPage,
            selectedGenre = selectedGenre,
            totalPages = totalPages
        )

    }

    AnimatedVisibility(
        visible = topRatedState.isLoading
    ) {
        onLoading()
    }

    AnimatedVisibility(visible = topRatedState.error.isNotEmpty()) {
        onError(topRatedState.error)
    }


}





}




@Composable
private fun MovieCollection(
    trendingState: MovieState,
    genresState: GenresState,
    topRatedState: MovieState,
    popularState: MovieState,
    upComingState: MovieState,
    nowPlayingState: MovieState,
    currentPage: Int,
    totalPages: Int,
    selectedGenre: Genre,
    onMenuItemSelected: (String) -> Unit,
    onNextClicked:()-> Unit,
    onPreviewsClicked:()-> Unit,
    onMovieItemClicked: (Movie) -> Unit,
    onGenreItemClicked: (Genre) -> Unit,
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 10.dp ,vertical = 20.dp)
    ){
        val isDateEmpty = checkDataEmpty<Boolean>(topRatedState,upComingState,nowPlayingState,popularState,trendingState)



        // pagination and menu
        item {
            Menu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                items = homeMenuItems,
                onItemClicked = { onMenuItemSelected(it) }
            )
        }



        item {
            GenresCollection(
                selectedGenre = selectedGenre,
                genres = genresState.data ,
                onItemClicked = { genre ->
                    onGenreItemClicked(genre)
                }
            )
        }


        item {

            ScrollableMovieItems(
                sectionName = "Trending",
                items = trendingState.data,
                landscape = true,
                onItemClicked = { onMovieItemClicked(it)}
            )
        }


        item {
            ScrollableMovieItems(
                sectionName = "Top Rated",
                items = topRatedState.data,
                onItemClicked = { onMovieItemClicked(it)}
            )
        }

        item {
            ScrollableMovieItems(
                sectionName = "Popular",
                items = popularState.data,
                onItemClicked = {onMovieItemClicked(it)}
            )
        }

        item {
            ScrollableMovieItems(
                sectionName = "Upcoming",
                items = upComingState.data,
                onItemClicked = {onMovieItemClicked(it)}
            )
        }

        item {
            ScrollableMovieItems(
                sectionName = "Now Playing",
                items = nowPlayingState.data,
                onItemClicked = { onMovieItemClicked(it)}
            )
        }




        // paging item
        item {
            PaginationItem(
                isVisible = !isDateEmpty,
                modifier = Modifier.fillMaxWidth(),
                currentPage = currentPage ,
                totalPages = totalPages,
                onPreviousClicked = { onPreviewsClicked()},
                onNextClicked = { onNextClicked() }
            )
        }


    }
}

@Composable
private fun HomeAppBar(
    onProfileClicked:()-> Unit,
    onSearchClicked:()-> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(40.dp)
                .weight(0.3f)
                .clickable { onProfileClicked() },
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "icon profile"
        )

        Text(
            modifier = Modifier.weight(0.6f),
            text = "Movie App",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Image(
            modifier = Modifier.size(40.dp)
                .weight(0.3f)
                .clickable { onSearchClicked() },
            imageVector = Icons.Default.Search,
            contentDescription = "icon search",
            colorFilter = ColorFilter.tint(Color.White)
        )

    }
}

@Composable
private fun GenresCollection(
    selectedGenre: Genre,
    genres: List<Genre> = emptyList(),
    onItemClicked: (Genre) -> Unit,
){

    AnimatedVisibility(visible = genres.isNotEmpty()) {
        Column {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Genres",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            LazyRow(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {

                itemsIndexed(items = genres) { index, genre ->
                    SelectableGenreChip(
                        genre = genre.name,
                        selected = genre == selectedGenre,
                        onclick = {
                            onItemClicked(genre)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableGenreChip(
    genre: String,
    selected: Boolean,
    onclick: () -> Unit
) {

    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFA0A1C2) else DarkBlue.copy(alpha = 0.5F),
        animationSpec = tween(
            durationMillis = if (selected) 100 else 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .clip(CircleShape)
            .background(
                color = animateChipBackgroundColor
            )
            .height(32.dp)
            .widthIn(min = 80.dp)
            /*.border(
                width = 0.5.dp,
                color = Color(0xC69495B1),
                shape = CircleShape
            )*/
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            }
    ) {
        Text(
            text = genre,
            fontWeight = if (selected) Normal else Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Center),
            color = if (selected) Color(0XFF180E36) else Color.White.copy(alpha = 0.80F)
        )
    }
}




private fun <T> checkDataEmpty(vararg states: Any?): Boolean {
    for (state in states) {
        when (state) {
            is MovieState -> if (state.data.isEmpty()) return true
            else -> continue
        }
    }
    return false
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    val testList  = ArrayList<Movie>()
    repeat(10){
        testList.add(
            Movie(
                id= it,
                coverUrl ="https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
                imgUrl = "https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
                releaseDate = "2005-11-16",
                title = "Harry Potter and the Chamber of Secrets",
                rate = 7.718,
                ratersCount = 20624,
                description = "Cars fly, trees fight back, and a mysterious house-elf comes to warn Harry Potter at the start of his second year at Hogwarts. Adventure and danger await when bloody writing on a wall announces: The Chamber Of Secrets Has Been Opened. To save Hogwarts will require all of Harry, Ron and Hermione’s magical abilities and courage.",
                genreIds = emptyList(),
                mediaType = "",
                name = ""
            )
        )
    }
    HomeContent(
        trendingState =  MovieState( data = testList),
        genresState = GenresState(),
        topRatedState =  MovieState(data = testList),
        popularState =  MovieState(data = testList),
        upComingState = MovieState(data = testList),
        nowPlayingState = MovieState(data = testList),
        totalPages = Constants.TOTAL_PAGES,
        currentPage = Constants.INITIAL_PAGE,
        onNextClicked = {},
        onPreviewsClicked = {},
        selectedGenre = Genre(),
        onMenuItemSelected = {},
        onError = {},
        onLoading = {}
    )

}