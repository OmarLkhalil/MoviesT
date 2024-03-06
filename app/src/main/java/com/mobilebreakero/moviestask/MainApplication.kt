package com.mobilebreakero.moviestask

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mobilebreakero.moviestask.ui.RefreshDataWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupPeriodicDataRefresh()
    }

    private fun setupPeriodicDataRefresh() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            repeatInterval = 4,
            repeatIntervalTimeUnit = TimeUnit.SECONDS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(periodicWorkRequest)
    }
}