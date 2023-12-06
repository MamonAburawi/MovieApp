package com.mamon.movieapp.screens.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamon.movieapp.screens.state.MovieState
import com.mamon.movieapp.screens.state.CastState
import com.mamon.movieapp.screens.state.MovieDetailState
import com.use_case.GetCastsUseCase
import com.use_case.GetMovieDetailUseCase
import com.use_case.GetTopRatedMoviesUseCase
import com.utils.Constants
import com.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getCastsUseCase: GetCastsUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    stateHandle: SavedStateHandle
): ViewModel() {
    private val args = MovieDetailArgs(stateHandle)

    private val _stateMovieDetail = MutableStateFlow(MovieDetailState())
    val stateMovieDetail = _stateMovieDetail.asStateFlow()

    private val _stateCasts = MutableStateFlow(CastState())
    val stateCasts = _stateCasts.asStateFlow()

    private val _genresId = MutableStateFlow<List<Int>>(emptyList())
    val genresId = _genresId.asStateFlow()

    private val _stateTopRating = MutableStateFlow(MovieState())
    val stateTopRating = _stateTopRating.asStateFlow()


    init {
        refresh()
    }

    fun refresh(){
        val movieId = args.movieId
        getMovieDetail(movieId)
        getCasts(movieId)
        getTopRatedMovies(Random().nextInt(Constants.TOTAL_PAGES - 1) + 2)
    }

    private fun getMovieDetail(movieId: Int) {
        getMovieDetailUseCase(movieId).onEach { result ->
            when(result){
                is Resource.Loading -> { _stateMovieDetail.value = MovieDetailState(isLoading = true)
                }
                is Resource.Success -> { _stateMovieDetail.value = MovieDetailState(data = result.data) }
                is Resource.Error -> { _stateMovieDetail.value = MovieDetailState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    private fun getCasts(movieId: Int) {
        getCastsUseCase(movieId).onEach { result ->
            when(result){
                is Resource.Loading -> { _stateCasts.value = CastState(isLoading = true)
                }
                is Resource.Success -> { _stateCasts.value = CastState(data = result.data ?: emptyList()) }
                is Resource.Error -> { _stateCasts.value = CastState(error = result.message?:"unknown error occured")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies(page: Int) {
        getTopRatedMoviesUseCase(page).onEach { result ->
            when(result){
                is Resource.Loading -> { _stateTopRating.value = MovieState(isLoading = true) }
                is Resource.Success -> { _stateTopRating.value = MovieState(data  = result.data?: emptyList()) }
                is Resource.Error -> { _stateTopRating.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> { }
            }
        }.launchIn(viewModelScope)
    }



}