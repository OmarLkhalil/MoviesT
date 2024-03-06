package com.mobilebreakero.moviestask.data.repoImpl

import com.mobilebreakero.moviestask.data.dao.MovieDao
import com.mobilebreakero.moviestask.data.endpoint.MoviesEndPoint
import com.mobilebreakero.moviestask.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesCacheRepo @Inject constructor(
    private val movieDao: MovieDao,
    private val api: MoviesEndPoint,
) {
    val genresIds = listOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
        23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 36, 37, 38
    )

    fun getMovies() = networkBoundResource(
        query = {
            movieDao.getMovies()
        },
        fetch = {
            api.getMovies(
                page = 10,
                language = "en-US",
                genresId = genresIds.random().toString()
            ).results
        },
        saveFetchResult = { movies ->
            movieDao.insertAll(movies)
        }
    )

    suspend fun refreshMovies() {

        val moviesResponse =
            api.getMovies(page = 10, language = "en-US", genresId = genresIds.random().toString())

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