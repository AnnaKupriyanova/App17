package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.domain.RepositoryImpl
import com.example.myapplication.data.database.WeatherDatabase
import com.example.myapplication.data.network.WeatherAPI
import com.example.myapplication.domain.Repository
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var weatherAPI: WeatherAPI
        lateinit var weatherDatabase: WeatherDatabase
        lateinit var repository: Repository
    }

    override fun onCreate() {
        super.onCreate()
        initApi()
        initDatabase()
        initTimber()
        initRepository()
    }

    private fun initRepository() {
        repository = RepositoryImpl(apiService = weatherAPI, weatherDatabase = weatherDatabase)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initApi() {
        weatherAPI = WeatherAPI.createAPI()
    }

    private fun initDatabase() {
        weatherDatabase = Room.databaseBuilder(
            this,
            WeatherDatabase::class.java,
            "weather")
            .fallbackToDestructiveMigration()
            .build()
    }
}