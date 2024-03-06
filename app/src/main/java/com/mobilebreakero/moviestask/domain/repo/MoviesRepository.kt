package com.mobilebreakero.moviestask.domain.repo

import com.mobilebreakero.moviestask.domain.model.DetailsDto
import com.mobilebreakero.moviestask.domain.model.MoviesModel


interface MoviesRepository {
    suspend fun getMovies(language: String, page: Int, genresId: String): MoviesModel
    suspend fun getDetails(movieId: Int, language: String): DetailsDto
}