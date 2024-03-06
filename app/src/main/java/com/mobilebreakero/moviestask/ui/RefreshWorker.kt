package com.mobilebreakero.moviestask.ui

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mobilebreakero.moviestask.data.endpoint.MoviesEndPoint
import com.mobilebreakero.moviestask.data.local.MovieDatabase
import com.mobilebreakero.moviestask.data.repoImpl.MoviesCacheRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.Retrofit

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(MoviesEndPoint::class.java)
        val dao = MovieDatabase.getDatabase(applicationContext).movieDao()
        val moviesCacheRepo = MoviesCacheRepo(api = api, movieDao = dao)
        return try {
            moviesCacheRepo.refreshMovies()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}