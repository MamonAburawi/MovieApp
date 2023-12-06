package com.use_case

import com.module.Movie
import com.repository.MovieRepositoryImpl
import com.utils.Resource
import com.utils.TimeWindow
import com.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
) {
    operator fun invoke(time: String = TimeWindow.Day.time): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getTrendingMovies(time).results.map { it.toMovie() }
            emit(Resource.Success(movies))
        }
        catch (e: HttpException){ // no internet connection or server
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }

    }
}