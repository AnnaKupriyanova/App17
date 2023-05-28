package com.example.myapplication.domain

sealed class WeatherFrom {
    data class DataFromNetwork(val weatherData: WeatherData) : WeatherFrom()
    data class DataFromDatabase(val weatherData: WeatherData) : WeatherFrom()
}