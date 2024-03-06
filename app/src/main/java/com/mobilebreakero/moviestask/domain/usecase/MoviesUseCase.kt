package com.mobilebreakero.moviestask.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mobilebreakero.moviestask.domain.model.MovieItem
import com.mobilebreakero.moviestask.domain.paginationsource.MoviesPaginationSource
import com.mobilebreakero.moviestask.domain.repo.MoviesRepository
import com.mobilebreakero.moviestask.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MoviesUseCase @Inject constructor
    (private val repository: MoviesRepository) {

    operator fun invoke(
        language: String,
        genresId: String,
    ): Flow<DataState<Pager<Int, MovieItem>>> = flow {

        try {
            emit(DataState.Loading())
            val getMovies = Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    MoviesPaginationSource(
                        repository = repository,
                        cateId = genresId,
                        lang = language
                    )
                }
            )
            emit(DataState.Success(getMovies))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "An unexpected error occured"))
        }
    }

}