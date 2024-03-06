package com.mobilebreakero.moviestask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.mobilebreakero.moviestask.data.repoImpl.MoviesCacheRepo
import com.mobilebreakero.moviestask.domain.model.DetailsDto
import com.mobilebreakero.moviestask.domain.model.MovieItem
import com.mobilebreakero.moviestask.domain.usecase.DetailsUseCase
import com.mobilebreakero.moviestask.domain.usecase.MoviesUseCase
import com.mobilebreakero.moviestask.domain.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val detailsUseCase: DetailsUseCase,
    private val moviesCacheRepo: MoviesCacheRepo,
) : ViewModel() {


    val moviesCached = moviesCacheRepo.getMovies().asLiveData()

    private val _state = MutableStateFlow(MovieState())
    val movies: StateFlow<MovieState>
        get() = _state

    init {
        getMovies("en-US", "28")
    }

    fun getMovies(language: String, genresId: String) {
        moviesUseCase(
            genresId = genresId,
            language = language,
        ).onEach { result ->
            when (result) {
                is DataState.Success -> {
                    Log.e("success", "Data: ${result.data}")
                    _state.value = MovieState(movie = result.data)
                }

                is DataState.Error -> {
                    Log.e("error", "Error: ${result.message}")
                    _state.value =
                        MovieState(error = result.message ?: "An unexpected error occurred")
                }

                is DataState.Loading -> {
                    Log.e("loading", "Loading")
                    _state.value = MovieState(isLoading = true)

                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private val _detailsState = MutableStateFlow(DetailsState())

    val detailsState: StateFlow<DetailsState>
        get() = _detailsState

    fun getMovieDetails(movieId: Int, language: String) {
        viewModelScope.launch {

            detailsUseCase(movieId = movieId, language = language)
                .onEach { result ->
                    when (result) {
                        is DataState.Success -> {
                            Log.e("success", "Data: ${result.data}")
                            _detailsState.value = DetailsState(details = result.data)

                        }

                        is DataState.Error -> {
                            Log.e("error", "Error: ${result.message}")
                            _detailsState.value =
                                DetailsState(
                                    error = result.message ?: "An unexpected error occurred"
                                )
                        }

                        is DataState.Loading -> {
                            Log.e("loading", "Loading")
                            _detailsState.value = DetailsState(isLoading = true)
                        }

                        else -> {}
                    }
                }
                .launchIn(viewModelScope)
        }
    }

}

data class DetailsState(
    val isLoading: Boolean = false,
    val details: DetailsDto? = null,
    val error: String = "",
)

data class MovieState(
    val isLoading: Boolean = false,
    val movie: Pager<Int, MovieItem>? = null,
    val error: String = "",
)
