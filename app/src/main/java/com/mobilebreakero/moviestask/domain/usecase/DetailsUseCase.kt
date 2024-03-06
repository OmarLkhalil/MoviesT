package com.mobilebreakero.moviestask.domain.usecase

import com.mobilebreakero.moviestask.domain.model.DetailsDto
import com.mobilebreakero.moviestask.domain.repo.MoviesRepository
import com.mobilebreakero.moviestask.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(
        movieId: Int,
        language: String,
    ): Flow<DataState<DetailsDto>> = flow {
        try {
            emit(DataState.Loading())
            val details = moviesRepository.getDetails(movieId, language)
            emit(DataState.Success(details))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "An unexpected error occured"))
        }
    }
}