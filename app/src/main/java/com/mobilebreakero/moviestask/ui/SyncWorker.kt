package com.mobilebreakero.moviestask.ui

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mobilebreakero.moviestask.data.repoImpl.MoviesCacheRepo
import java.util.concurrent.TimeUnit

class RefreshDataWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val moviesCacheRepo: MoviesCacheRepo
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            moviesCacheRepo.refreshMovies()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}