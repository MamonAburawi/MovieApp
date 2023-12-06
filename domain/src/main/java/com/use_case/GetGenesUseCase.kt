package com.use_case


import com.dto.movie_detail.Genre
import com.repository.MovieRepositoryImpl
import com.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGenesUseCase @Inject constructor(
    private val repository: MovieRepositoryImpl
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        try {
            emit(Resource.Loading())
            val genres = repository.getGenres().genres
            val list = genres.toMutableList()
            list.add(index = 0, Genre(id = 0, name = "All")) // add new genre item
            emit(Resource.Success(list))
        }
        catch (e: HttpException){ // no internet connection or server
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
        }

    }

}