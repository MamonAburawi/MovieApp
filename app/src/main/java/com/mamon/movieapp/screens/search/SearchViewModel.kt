@file:OptIn(FlowPreview::class)

package com.mamon.movieapp.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamon.movieapp.screens.state.MovieState
import com.use_case.SearchMoviesUseCase
import com.utils.Constants
import com.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMovieMoviesUseCase: SearchMoviesUseCase
): ViewModel() {

    private val _status = MutableStateFlow(MovieState())
    val status = _status.asStateFlow()

    private val _query = MutableStateFlow("")

    init {
        _query.debounce(Constants.DELAY_RESPONSE).onEach { query ->
            findMovies(query)
        }.launchIn(viewModelScope)

    }

    fun search(query: String){
        _query.value = query
    }

    fun refresh(){
        _query.value = _query.value
    }

     private fun findMovies(query: String) {
        getMovieMoviesUseCase(query).onEach { result ->
            when(result){
                is Resource.Loading -> { _status.value = MovieState(isLoading = true) }
                is Resource.Success -> { _status.value = MovieState(data  = result.data?: emptyList()) }
                is Resource.Error -> { _status.value = MovieState(error = result.message?:"unknown error occured")
                }
                else -> { }
            }
        }.launchIn(viewModelScope)
    }



}