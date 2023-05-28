package com.example.myapplication.domain

import com.example.myapplication.Constants.API_CITY
import com.example.myapplication.Constants.API_KEY
import com.example.myapplication.Constants.API_LANGUAGE
import com.example.myapplication.Constants.API_UNITS
import com.example.myapplication.data.database.WeatherDatabase
import com.example.myapplication.data.database.WeatherEntity
import com.example.myapplication.data.network.WeatherAPI
import com.example.myapplication.data.toDomain
import com.example.myapplication.data.toEntity
import timber.log.Timber
import com.example.myapplication.data.database.CityEntity
import com.example.myapplication.data.database.PositionEntity
import com.example.myapplication.domain.Position

class RepositoryImpl(
    private val apiService: WeatherAPI,
    private val weatherDatabase: WeatherDatabase
) : Repository {
    override suspend fun getWeather(lat: String, lon: String): WeatherFrom {
        return try {
            val weather = apiService.getForecast(lat, lon, API_KEY, API_UNITS, API_LANGUAGE)
            updateWeatherDB(weather.list.toEntity(), weather.city.toEntity(), Position(lat, lon).toEntity())
            WeatherFrom.DataFromNetwork(weather.toDomain())
        } catch (exception: Exception) {
            val weather = WeatherData(getWeatherDB().toDomain(), getCityDB().toDomain(), getPositionDB().toDomain())
            Timber.tag("TAG").d("Load from Database")
            Timber.e(exception)
            WeatherFrom.DataFromDatabase(weather)
        }
    }

    private suspend fun getPositionDB() = weatherDatabase.weatherDao().getPosition()

    private suspend fun getCityDB() = weatherDatabase.weatherDao().getCity()

    private suspend fun getWeatherDB() = weatherDatabase.weatherDao().getWeather()

    private suspend fun updateWeatherDB(
        weatherList: List<WeatherEntity>,
        cityName: CityEntity,
        position: PositionEntity
    ) = weatherDatabase.weatherDao().updateWeather(weatherList, cityName, position)
}