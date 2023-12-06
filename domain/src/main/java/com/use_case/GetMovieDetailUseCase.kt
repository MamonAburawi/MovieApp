package com.use_case

import com.module.MovieDetail
import com.repository.MovieRepositoryImpl
import com.utils.Resource
import com.utils.toMovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getMovieById(movieId).toMovieDetail()
            emit(Resource.Success(movie))
        }
        catch (e: HttpException){ // no internet connection or server
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }

    }

}