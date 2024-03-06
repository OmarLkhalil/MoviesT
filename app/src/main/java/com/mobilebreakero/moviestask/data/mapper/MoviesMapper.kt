package com.mobilebreakero.moviestask.data.mapper

import com.mobilebreakero.moviestask.data.dto.MoviesDto
import com.mobilebreakero.moviestask.data.dto.ResultsItem
import com.mobilebreakero.moviestask.domain.model.MovieItem
import com.mobilebreakero.moviestask.domain.model.MoviesModel
import javax.inject.Inject


class MoviesMapper @Inject constructor() {

    fun fromRemoteMoviesToMoviesModel(obj: MoviesDto): MoviesModel {
        return MoviesModel(
            movieList = fromRemoteMovieItemDtoToMovieItem(obj.results),
            page = obj.page,
            totalPages = obj.totalPages,
            totalResults = obj.totalResults
        )
    }

    private fun fromRemoteMovieItemDtoToMovieItem(obj: List<ResultsItem?>?): List<MovieItem> {
        return obj!!.map {
            MovieItem(
                id = it!!.id,
                posterUrl = it.posterPath,
                name = it.title,
            )
        }
    }
}