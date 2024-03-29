package com.mobilebreakero.moviestask.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class MoviesModel(
    val movieList: List<MovieItem> = emptyList(),
    val page: Long? = 0L,
    val totalPages: Long? = 0L,
    val totalResults: Long? = 0L,
)

data class MovieItem(
    val id: Long? = 0L,
    val posterUrl: String? = "",
    val name: String? = "",
    val videoUrl: String? = "",
)
