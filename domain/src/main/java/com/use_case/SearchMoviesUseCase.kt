package com.use_case

import com.utils.toMovie
import com.utils.Resource
import com.module.Movie
import com.repository.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
) {

    operator fun invoke(query: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.searchByName(query).results.map { it.toMovie() }
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