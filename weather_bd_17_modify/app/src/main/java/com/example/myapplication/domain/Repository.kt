package com.example.myapplication.domain

interface Repository {
    suspend fun getWeather(lat: String, lon: String): WeatherFrom
}