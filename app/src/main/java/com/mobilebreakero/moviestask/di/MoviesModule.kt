package com.mobilebreakero.moviestask.di

import android.content.Context
import androidx.room.Room
import com.mobilebreakero.moviestask.data.dao.MovieDao
import com.mobilebreakero.moviestask.data.endpoint.MoviesEndPoint
import com.mobilebreakero.moviestask.data.local.MovieDatabase
import com.mobilebreakero.moviestask.data.mapper.MoviesMapper
import com.mobilebreakero.moviestask.data.repoImpl.MoviesCacheRepo
import com.mobilebreakero.moviestask.data.repoImpl.MoviesRepoImplementation
import com.mobilebreakero.moviestask.domain.repo.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(api: MoviesEndPoint, mapper: MoviesMapper): MoviesRepository {
        return MoviesRepoImplementation(api, mapper)
    }

    @Provides
    @Singleton
    fun provideMoviesCacheRepository(movieDao: MovieDao, api: MoviesEndPoint): MoviesCacheRepo {
        return MoviesCacheRepo(movieDao, api)
    }
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}