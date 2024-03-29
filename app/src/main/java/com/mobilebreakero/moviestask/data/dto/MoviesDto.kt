package com.mobilebreakero.moviestask.data.dto


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

@Parcelize
data class MoviesDto(

    @field:SerializedName("page")
    val page: Long? = null,

    @field:SerializedName("total_pages")
    val totalPages: Long? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem>,

    @field:SerializedName("total_results")
    val totalResults: Long? = null
) : Parcelable

@Entity(tableName = "movie")
@Parcelize
data class ResultsItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,


    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: @RawValue Float? = null,

    @field:SerializedName("vote_average")
    val voteAverage: @RawValue Double? = null,

    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null
) : Parcelable
