package com.mobilebreakero.moviestask.data.endpoint

import com.mobilebreakero.moviestask.data.dto.MoviesDto
import com.mobilebreakero.moviestask.domain.model.DetailsDto
import com.mobilebreakero.moviestask.domain.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesEndPoint {
    @GET(Constants.MoviesEndPoint)
    suspend fun getMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("with_genres") genresId: String,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
    ): MoviesDto

    @GET("${Constants.DetailsEndPoint}/{movieId}")
    suspend fun getDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): DetailsDto
}