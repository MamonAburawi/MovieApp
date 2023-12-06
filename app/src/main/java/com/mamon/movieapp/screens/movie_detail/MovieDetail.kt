package com.mamon.movieapp.screens.movie_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mamon.movieapp.R
import com.mamon.movieapp.ui.theme.DarkBlue
import com.module.MovieDetail
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity

import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.mamon.movieapp.component.CircleButton
import com.mamon.movieapp.component.GradientBackgroundBox
import com.mamon.movieapp.screens.home.HomeViewModel
import com.mamon.movieapp.component.ExpandableText
import com.mamon.movieapp.component.GenresListViewer
import com.mamon.movieapp.component.OnError
import com.mamon.movieapp.component.ScrollableMovieItems
import com.mamon.movieapp.screens.state.GenresState
import com.mamon.movieapp.screens.state.MovieState
import com.mamon.movieapp.screens.state.CastState
import com.mamon.movieapp.screens.state.MovieDetailState
import com.mamon.movieapp.ui.theme.YoutubeColor
import com.mamon.movieapp.util.trimTitle
import com.module.Cast
import com.module.Movie

import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.utils.getGenresById
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun MovieDetails(
    navController: NavController,
    detailsViewModel: MovieDetailViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val date = SimpleDateFormat.getDateTimeInstance().format(Date())
    val detailState = detailsViewModel.stateMovieDetail.collectAsState().value
    val genreState = homeViewModel.stateGenres.collectAsState().value
    val stateCasts = detailsViewModel.stateCasts.collectAsState().value
    val stateTopRated = detailsViewModel.stateTopRating.collectAsState().value


    Log.d("MovieDetail","cast: ${stateCasts.data.size}")

    DetailsContent(
        stateTopRatingState = stateTopRated,
        stateCasts = stateCasts,
        detailState = detailState,
        genreState = genreState,
        onMovieItemClicked = {
            navController.navigateToMovieDetail(it.id)
        },
        onBackClicked = {
            navController.navigateUp()
        },
        onLoading = {
            CircularProgressIndicator()
        },
        onError = { error ->
            OnError(
                error = "We're unable to load the data at this time \n" +
                        " Please check your internet connection and try again.",
                onRefreshClicked = {
                    detailsViewModel.refresh()
                }
            )

        }


    )


}


@Composable
fun DetailsContent(
    stateTopRatingState: MovieState,
    stateCasts: CastState,
    detailState: MovieDetailState,
    genreState: GenresState,
    onBackClicked: () -> Unit,
    onMovieItemClicked:(Movie) -> Unit,
    onLoading: @Composable () -> Unit,
    onError: @Composable (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                val bgCoverHeight = 250


                Cover(
                    height = bgCoverHeight,
                    onLoading = {
                       CircularProgressIndicator()
                    },
                    detailState = detailState
                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = (bgCoverHeight * 0.33).dp),
                ) {


                    if (detailState.data != null) {


                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            MovieInfoCard(checkNotNull(detailState.data))

                            Column(
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                            ) {

                                val genresId = genreState.data.map { it.id }
                                val filteredGenres = getGenresById(detailState.data!!.genres, genresId)


                                WatchTrailer(
                                    modifier = Modifier.padding(vertical = 15.dp),
                                    releaseDate = detailState.data?.releaseDate?: "",
                                    title = detailState.data?.title?: "",
                                )


                                if (detailState.data != null) {
                                    GenresListViewer(genres = filteredGenres)
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                ExpandableText(
                                    text = detailState.data?.description ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CastList(stateCasts = stateCasts)
                                Spacer(modifier = Modifier.height(15.dp))
                                ScrollableMovieItems(
                                    sectionName = "Other",
                                    items = stateTopRatingState.data,
                                    onItemClicked = {
                                        onMovieItemClicked(it)
                                    }
                                )
                            }


                        }
                    }


                }
            }
        }

        AnimatedVisibility(detailState.isLoading) {
            onLoading()
        }

        AnimatedVisibility (detailState.error.isNotEmpty()) {
            onError(detailState.error)
        }


        CircleButton(
            size = 40,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 25.dp, start = 25.dp),
            ic = Icons.Default.ArrowBack,
            onClicked = { onBackClicked() }
        )


    }




}

private fun watchTrailer(context: Context, query: String){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$query"))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(context,intent, null)
}

@Composable
fun WatchTrailer(
    modifier: Modifier = Modifier,
    title: String,
    releaseDate: String,
) {
    val context = LocalContext.current
    AnimatedVisibility(visible = title.isNotEmpty()) {
        Row(
            modifier = modifier
                .clickable(
                    interactionSource = remember{ MutableInteractionSource() },
                    indication = null
                ) {  watchTrailer(
                    context = context,
                    query = "Trailer $title $releaseDate"
                ) }
                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                .shadow(elevation = 3.dp, spotColor = Color.White)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text ="Watch Trailer",
                Modifier,
                color = YoutubeColor,
                fontWeight = Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_youtube),
                contentDescription = "icon youtube"
            )
        }
    }

}

@Composable
fun Cover(
    detailState: MovieDetailState,
    height: Int,
    onLoading: @Composable () -> Unit = {},
    onFailure: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        contentAlignment = Alignment.Center
    ){

        if (detailState.isLoading){
            onLoading()
        }else{
            val url = detailState.data?.coverUrl ?: ""
            CoilImage(
                imageModel = url,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .height(height.dp),
                failure = {
                    onFailure()
                },
                shimmerParams = ShimmerParams(
                    baseColor = Color.Transparent,
                    highlightColor = Color.Transparent,
                    durationMillis = 500,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                contentScale = Crop,
                contentDescription = "Header backdrop image",
            )
        }


    }
}

@Composable
fun CastList(stateCasts: CastState){
    AnimatedVisibility(visible = stateCasts.data.isNotEmpty()) {
        Column {
            Text(
                text = "Cast",
                fontSize = 18.sp,
                fontWeight = Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(items = stateCasts.data){
                    CastMember(cast = it)
                }
            }

        }


    }
}


@Composable
fun MovieInfoCard(details: MovieDetail) {
    Box{
        GradientBackgroundBox(
            modifier = Modifier.height(200.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CoilImage(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .size(130.dp, 195.dp)
                    .clickable {},
                imageModel = details.imgUrl,
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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_image_not_available),
                            contentDescription = "no image"
                        )
                    }
                },
                contentScale = Crop,
                circularReveal = CircularReveal(duration = 1000),
                contentDescription = "Movie item"
            )

            Spacer(modifier = Modifier.width(20.dp))


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(0.6f)
            ) {


                Text(
                    text = trimTitle(details.title),
                    modifier = Modifier
                        .fillMaxWidth(0.85F),
                    maxLines = 2,
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = Color.White.copy(alpha = 0.78F)
                )


                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = details.releaseDate,
                    fontSize = 15.sp,
                    fontWeight = Light,
                    color = Color.White.copy(alpha = 0.65F)
                )

                Spacer(modifier = Modifier.height(5.dp))

                RatingBar(
                    value = (details.rate / 2).toFloat(),
                    config = RatingBarConfig()
                        .style(RatingBarStyle.Normal)
                        .isIndicator(true)
                        .activeColor(Color(0XFFC9F964))
                        .hideInactiveStars(false)
                        .inactiveColor(Color.LightGray.copy(alpha = 0.3F))
                        .stepSize(StepSize.HALF)
                        .numStars(5)
                        .size(16.dp)
                        .padding(4.dp),
                    onValueChange = {},
                    onRatingChanged = {}
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Text(
                            text = when (details.adult) {
                                true -> "18+"
                                else -> "PG"
                            },
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .background(
                                    if (details.adult) Color(0xFFFF7070) else Color.Transparent,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(3.dp),
                            color = Color.White.copy(alpha = 0.65F),
                            fontSize = 12.sp,
                        )
                    }


                    Icon(
                        painter = painterResource(id = R.drawable.add_circle),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                // do click function
                            },
                        contentDescription = "reviews icon"
                    )


                }

            }


        }
    }

}


@Composable
fun CastMember(cast: Cast?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CoilImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp),
            imageModel = cast?.imgUrl ?: "",
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier.size(70.dp),
                        imageVector = Icons.Default.AccountCircle,
                        tint = Color.LightGray,
                        contentDescription = null
                    )
                }
            },
            contentScale = Crop,
            circularReveal = CircularReveal(duration = 1000),
            contentDescription = "cast image"
        )
        Text(
            text = trimTitle(cast?.name?: "", textLength = 8),
            maxLines = 1,
            color = Color.White.copy(alpha = 0.5F),
            fontSize = 14.sp,
        )
        Text(
            text = trimTitle(cast?.department?: "",textLength = 8),
            maxLines = 1,
            color = Color.White.copy(alpha = 0.45F),
            fontSize = 12.sp,
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailsPreview() {
    val state = MovieDetailState(
        data =  MovieDetail(
            id= 6545,
            coverUrl ="https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
            imgUrl = "https://image.tmdb.org/t/p/w500/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
            releaseDate = "2005-11-16",
            title = "Harry Potter and the Chamber of Secrets",
            rate = 7.718,
            description = "Cars fly, trees fight back, and a mysterious house-elf comes to warn Harry Potter at the start of his second year at Hogwarts. Adventure and danger await when bloody writing on a wall announces: The Chamber Of Secrets Has Been Opened. To save Hogwarts will require all of Harry, Ron and Hermione’s magical abilities and courage.",
            budget = 464654,
            homePage = "65464",
            imdbId = "64654",
            originalLanguage = "5464",
            ratesCount = 45,
            tagLine = "sdf",
            revenue = 454,
            productionCompany = emptyList(),
            adult = false,
            genres = emptyList()
        )
    )
    DetailsContent(
        stateTopRatingState = MovieState(),
        detailState = state,
        genreState = GenresState(),
        onBackClicked = {},
        onLoading = {},
        onError = {},
        stateCasts = CastState(),
        onMovieItemClicked = {},
    )
}