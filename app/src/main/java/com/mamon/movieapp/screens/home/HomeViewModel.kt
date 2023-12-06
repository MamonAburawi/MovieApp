package com.mamon.movieapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dto.movie_detail.Genre
import com.mamon.movieapp.screens.state.GenresState
import com.mamon.movieapp.screens.state.MovieState
import com.mamon.movieapp.screens.state.MovieDetailState
import com.module.Movie
import com.use_case.GetGenesUseCase
import com.use_case.GetNowPlayingUseCase
import com.use_case.GetPopularMoviesUseCase
import com.use_case.GetTopRatedMoviesUseCase
import com.use_case.GetTrendingMoviesUseCase
import com.use_case.GetUpcomingMoviesUseCase
import com.utils.Constants
import com.utils.MediaType
import com.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getNowPlayingUseCase: GetNowPlayingUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

    private val _stateTrending = MutableStateFlow(MovieState())
    val stateTrending = _stateTrending.asStateFlow()

    private val _stateTopRating = MutableStateFlow(MovieState())
    val stateTopRating = _stateTopRating.asStateFlow()

    private val _stateUpComing = MutableStateFlow(MovieState())
    val stateUpComing = _stateUpComing.asStateFlow()

    private val _stateNowPlaying = MutableStateFlow(MovieState())
    val stateNowPlaying = _stateNowPlaying.asStateFlow()

    private val _statePopular = MutableStateFlow(MovieState())
    val statePopular = _statePopular.asStateFlow()

    private val _stateGenres = MutableStateFlow(GenresState())
    val stateGenres = _stateGenres.asStateFlow()


    private val _stateMovieDetail = MutableStateFlow(MovieDetailState())
    val stateMovieDetail = _stateMovieDetail.asStateFlow()

    private val _page = MutableStateFlow(Constants.INITIAL_PAGE)
    val page = _page.asStateFlow()

    private val _mediaType = MutableStateFlow(MediaType.Movie.type)
    val mediaType = _mediaType.asStateFlow()


    private val _genre = MutableStateFlow(Genre(name = "All", id = 0)) // default genre
    val genre = _genre.asStateFlow()


    private val _query = MutableStateFlow("")


    init {

        getGenresMovie()

        _page.debounce(Constants.DELAY_RESPONSE).onEach { currentPage ->
            refresh()
        }.launchIn(viewModelScope)


        _genre.debounce(Constants.DELAY_RESPONSE).onEach { genre ->
            refresh()
        }.launchIn(viewModelScope)


        _mediaType.debounce(Constants.DELAY_RESPONSE).onEach { type ->
            getTrendingMovies()
        }.launchIn(viewModelScope)

    }

    fun refresh(){
        getTopRatedMovies()
        getUpComingMovies()
        getNowPlayingMovies()
        getPopularMovies()
        getTrendingMovies()
    }

    fun nextPage(){
        _page.value = _page.value + 1
    }

    fun previewsPage(){
        _page.value = _page.value - 1
    }

    fun setGenre(genre: Genre) {
        _genre.value = genre
    }

    fun setMediaType(type: String){
        _mediaType.value = type
    }


    private fun handleAllFilter(movies: List<Movie>?, genre: Genre?, mediaType: String): List<Movie> {
        val applyMediaTypeFilter = handleMediaFilter(movies, mediaType)
        return handleGenreFilter(applyMediaTypeFilter, genre)
    }

    private fun handleGenreFilter(movies: List<Movie>?, genre: Genre?): List<Movie> {
        return if (genre != null && _genre.value.name != "All") {
            movies?.filter { it.genreIds.contains(genre.id) } ?: emptyList()
        } else {
            movies ?: emptyList()
        }
    }


    private fun handleMediaFilter(movies: List<Movie>?, mediaType: String): List<Movie> {
        return movies?.filter { it.mediaType == mediaType } ?: emptyList()
    }


    private fun getTrendingMovies() {
        getTrendingMoviesUseCase().onEach { result ->
            when(result){
                is Resource.Loading -> { _stateTrending.value = MovieState(isLoading = true) }
                is Resource.Success -> { _stateTrending.value = MovieState( data = handleAllFilter(movies = result.data,_genre.value,_mediaType.value)) }
                is Resource.Error -> { _stateTrending.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }



    fun getTopRatedMovies() {
        getTopRatedMoviesUseCase(_page.value).onEach { result ->
            when(result){
                is Resource.Loading -> { _stateTopRating.value = MovieState(isLoading = true) }
                is Resource.Success -> { _stateTopRating.value = MovieState(data = handleGenreFilter(movies = result.data,_genre.value)) }
                is Resource.Error -> { _stateTopRating.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> { }
            }
        }.launchIn(viewModelScope)
    }


    private fun getUpComingMovies() {
        getUpcomingMoviesUseCase(_page.value).onEach { result ->
            when(result){
                is Resource.Loading -> { _stateUpComing.value = MovieState(isLoading = true) }
                is Resource.Success -> { _stateUpComing.value = MovieState( data = handleGenreFilter(result.data,_genre.value))
                }
                is Resource.Error -> { _stateUpComing.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    private fun getPopularMovies() {
        getPopularMoviesUseCase(_page.value).onEach { result ->
            when(result){
                is Resource.Loading -> { _statePopular.value = MovieState(isLoading = true) }
                is Resource.Success -> { _statePopular.value = MovieState( data = handleGenreFilter(result.data,_genre.value)) }
                is Resource.Error -> { _statePopular.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    private fun getNowPlayingMovies() {
        getNowPlayingUseCase(_page.value).onEach { result ->
            when(result){
                is Resource.Loading -> { _stateNowPlaying.value = MovieState(isLoading = true) }
                is Resource.Success -> { _stateNowPlaying.value = MovieState( data  = handleGenreFilter(result.data,_genre.value)) }
                is Resource.Error -> { _stateNowPlaying.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }



    private fun getGenresMovie() {
        getGenresUseCase().onEach { result ->
            when(result){
                is Resource.Loading -> { _stateGenres.value = GenresState(isLoading = true) }
                is Resource.Success -> { _stateGenres.value = GenresState( data = result.data?: emptyList()) }
                is Resource.Error -> { _stateGenres.value = GenresState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }




//    fun getMovie(movieId: Int) {
//        getMovieUseCase(movieId).onEach { result ->
//            when(result){
//                is Resource.Loading -> { _stateMovieDetail.value = MovieDetailState(isLoading = true)}
//                is Resource.Success -> { _stateMovieDetail.value = MovieDetailState(data = result.data) }
//                is Resource.Error -> { _stateMovieDetail.value = MovieDetailState(error = result.message?:"unknown error occured")
//                }
//                else -> {}
//            }
//        }.launchIn(viewModelScope)
//    }



//    fun searchByName(query: String) {
//        searchMoviesUseCase(query).onEach { result ->
//            when(result){
//                is Resource.Loading -> { _state.value = HomeState(isLoading = true)}
//                is Resource.Success -> { _state.value = HomeState(playingNow = result.data ?: emptyList()) }
//                is Resource.Error -> { _state.value = HomeState(error = result.message?:"unknown error occured")
//                }
//                else -> {}
//            }
//        }.launchIn(viewModelScope)
//    }
//

}