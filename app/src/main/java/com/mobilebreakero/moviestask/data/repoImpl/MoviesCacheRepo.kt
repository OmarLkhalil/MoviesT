package com.mobilebreakero.moviestask.data.repoImpl

import com.mobilebreakero.moviestask.data.dao.MovieDao
import com.mobilebreakero.moviestask.data.endpoint.MoviesEndPoint
import com.mobilebreakero.moviestask.data.mapper.MoviesMapper
import com.mobilebreakero.moviestask.domain.model.MovieItem
import com.mobilebreakero.moviestask.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoviesCacheRepo(
    private val movieDao: MovieDao,
    private val api: MoviesEndPoint,
) {

    fun getMovies() = networkBoundResource(
        query = {
            movieDao.getMovies()
        },
        fetch = {
            api.getMovies(page = 10, language = "en-US").results
        },
        saveFetchResult = { movies ->
            movieDao.insertAll(movies)
        }
    )

    suspend fun refreshMovies() {
        val moviesResponse = api.getMovies(page = 10, language = "en-US")
        movieDao.deleteAll()
        movieDao.insertAll(moviesResponse.results)
    }

}


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(DataState.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { DataState.Success(it) }
        } catch (e: Exception) {
            query().map { DataState.Error(e.message ?: "", e) }
        }
    } else {
        query().map { DataState.Success(it) }
    }

    emitAll(flow)
}