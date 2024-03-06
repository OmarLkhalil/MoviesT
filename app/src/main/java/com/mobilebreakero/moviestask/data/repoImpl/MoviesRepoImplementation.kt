package com.mobilebreakero.moviestask.data.repoImpl

import com.mobilebreakero.moviestask.data.endpoint.MoviesEndPoint
import com.mobilebreakero.moviestask.data.mapper.MoviesMapper
import com.mobilebreakero.moviestask.domain.model.DetailsDto
import com.mobilebreakero.moviestask.domain.model.MoviesModel
import com.mobilebreakero.moviestask.domain.repo.MoviesRepository
import javax.inject.Inject

class MoviesRepoImplementation @Inject constructor(
    private val api: MoviesEndPoint,
    private val movieMapper: MoviesMapper,
) : MoviesRepository {
    override suspend fun getMovies(language: String, page: Int, genresId: String): MoviesModel {
        return movieMapper.fromRemoteMoviesToMoviesModel(api.getMovies(language = language, page = page))
    }

    override suspend fun getDetails(movieId: Int, language: String): DetailsDto {
        return api.getDetails(movieId = movieId)
    }
}