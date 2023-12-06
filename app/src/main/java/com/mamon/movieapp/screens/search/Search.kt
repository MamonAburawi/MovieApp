package com.mamon.movieapp.screens.search


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dto.movie_detail.Genre
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.mamon.movieapp.R
import com.mamon.movieapp.component.CircleButton
import com.mamon.movieapp.component.GenresListViewer
import com.mamon.movieapp.component.OnError
import com.mamon.movieapp.component.SearchBar
import com.mamon.movieapp.screens.home.HomeViewModel
import com.mamon.movieapp.screens.movie_detail.navigateToMovieDetail
import com.mamon.movieapp.screens.state.MovieState
import com.mamon.movieapp.ui.theme.DarkBlue
import com.mamon.movieapp.ui.theme.Purple
import com.mamon.movieapp.util.trimTitle
import com.module.Movie
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.utils.getGenresById

@Composable
fun Search(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state = searchViewModel.status.collectAsState().value
    val genreState = homeViewModel.stateGenres.collectAsState().value

    val allGenres = genreState.data
    val currentGenresIds = state.data.flatMap { it.genreIds }
    val genres = getGenresById(allGenres, currentGenresIds )

    SearchContent(
        state = state,
        genres = genres,
        onBackClicked = {
            navController.navigateUp()
        },
        onQuery = {
            searchViewModel.search(it)
        },
        onMovieClicked = {
            navController.navigateToMovieDetail(moveId = it.id)
        },
        onLoading = {
            CircularProgressIndicator()
        },
        onError = {
            OnError(
                refreshVisible = false,
                error = "We're unable to load the data at this time \n" +
                        " Please check your internet connection and try again.",
                onRefreshClicked = {
                    searchViewModel.refresh()
                }
            )
        }
    )


}


@Composable
fun SearchContent(
    state: MovieState = MovieState(),
    genres: List<Genre> = emptyList(),
    onMovieClicked: (Movie) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String) -> Unit = {},
    onQuery:(String) -> Unit = {},

) {

    Column(
        modifier = Modifier
            .background(DarkBlue)
            .fillMaxSize()
    ){
        var query by remember { mutableStateOf("") }
        var initSearch by remember { mutableStateOf(true) }

           Spacer(modifier = Modifier.height(20.dp))

           AppBar(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 25.dp, end = 25.dp),
               onBackClicked = onBackClicked,
               title = stringResource(id = R.string.search)
           )
           Spacer(modifier = Modifier.height(20.dp))
           SearchBar(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(horizontal = 20.dp),
               text = query,
               hint = stringResource(id = R.string.search) ,
               query = {
                   initSearch = false
                   query = it
                   onQuery(it)
               }
           )

        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            contentAlignment = Alignment.Center
        ) {

            if(state.data.isNotEmpty()) {
                ResultItemList(
                    modifier = Modifier
                        .fillMaxSize(),
                    results = state.data ,
                    genres = genres,
                    onClick = {
                        onMovieClicked(it)
                    }
                )
            }


            if (initSearch && query.isEmpty() && state.data.isEmpty() && state.error.isEmpty()){
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Do you have any particular mood in mind for the movie?",
                    textAlign = TextAlign.Center,
                    color = Color.White.copy(0.8f)
                )
            }
            if (!initSearch && state.data.isEmpty() && !state.isLoading && state.error.isEmpty()){
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Oops, we couldn't find any movies matching your search",
                    textAlign = TextAlign.Center,
                    color = Color.White.copy(0.8f)
                )
            }

            if (state.isLoading && !initSearch){
                onLoading()
            }

            if (state.error.isNotEmpty()){
                onError(state.error)
            }
        }

       }




}

@Composable
fun ResultItemList(
    modifier: Modifier = Modifier,
    genres: List<Genre>,
    results: List<Movie>,
    onClick:(Movie)-> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 15.dp)
    ) {
        items(items = results){
            ResultItem(
                movie = it,
                genres = genres,
                onClick = onClick
            )
        }
    }
}

@Composable
fun ResultItem(
    movie: Movie,
    genres: List<Genre> = emptyList(),
    onClick:(Movie)-> Unit
) {
    Card(
        colors = CardDefaults.cardColors(Purple)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(4.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClick(movie) }
        ) {
            val imageUrl = movie.imgUrl
            val type: String = when(movie.mediaType){
                "movie" -> stringResource(id = R.string.movie)
                "tv" -> stringResource(id = R.string.tv)
                else -> stringResource(id = R.string.movie)
            }
            val title = movie.title ?: movie.name ?: ""
            val releaseDate = movie.releaseDate ?: ""

            CoilImage(
                imageModel = imageUrl,
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                shimmerParams = ShimmerParams(
                    baseColor = Color.Transparent,
                    highlightColor = Color.Transparent,
                    durationMillis = 500,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                failure = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_image_not_available),
                            contentDescription = "no image"
                        )
                    }
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(duration = 1000),
                contentDescription = "Movie item",
            )



            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = 8.dp)
            ) {
                AnimatedVisibility (type.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        text = type,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                }
                AnimatedVisibility (title.isNotEmpty()){
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = trimTitle(title,20),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.65F)
                    )
                }
                AnimatedVisibility (releaseDate.isNotEmpty()){
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = releaseDate,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White.copy(alpha = 0.65F)
                    )
                }

                RatingBar(
                    modifier = Modifier.padding(top = 4.dp),
                    value = (movie.rate / 2).toFloat(),
                    config = RatingBarConfig()
                        .style(RatingBarStyle.Normal)
                        .isIndicator(true)
                        .activeColor(Color(0XFFC9F964))
                        .hideInactiveStars(false)
                        .inactiveColor(Color.LightGray.copy(alpha = 0.3F))
                        .stepSize(StepSize.HALF)
                        .numStars(5)
                        .size(16.dp)
                        .padding(2.dp),
                    onValueChange = {},
                    onRatingChanged = {}
                )

                AnimatedVisibility(visible = genres.isNotEmpty()) {
                    GenresListViewer(
                        itemColor = Color.Yellow.copy(alpha = 0.5f),
                        genres = genres,
                        size = 8
                    )
                }



            }
        }
    }
}


@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    onBackClicked:()-> Unit,
    title: String
) {
    Box(
        modifier = modifier
    ) {
        CircleButton(
            size = 40,
            modifier = Modifier
                .align(Alignment.CenterStart),
            ic = Icons.Default.ArrowBack,
            onClicked = { onBackClicked() }
        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview
@Composable
fun ResultItemPreview() {
  ResultItem(
      movie = Movie(
          mediaType = "movie",
          title = "The 100",
          releaseDate = "2014-09-13",
          rate = 4.0
  )){}
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchPreview() {
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
                mediaType = "movie",
                name = ""
            )
        )
    }
    SearchContent(
        state = MovieState(data = testList),
        onBackClicked = {}
    )
}