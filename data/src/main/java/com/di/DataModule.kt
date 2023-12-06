package com.di

import com.MovieApi
import com.repository.MovieRepository
import com.repository.MovieRepositoryImpl
import com.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun movieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(Constants.MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun movieRepoImpl(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }


}