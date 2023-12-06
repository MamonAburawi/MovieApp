package com.use_case

import com.module.Cast
import com.repository.MovieRepositoryImpl
import com.utils.Resource
import com.utils.toCast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCastsUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
) {
    operator fun invoke(movieId: Int): Flow<Resource<List<Cast>>> = flow {
        try {
            emit(Resource.Loading())
            val casts = repository.getCasts(movieId).cast.map { it.toCast() }
            emit(Resource.Success(casts))
        }
        catch (e: HttpException){ // no internet connection or server
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }
    }
}