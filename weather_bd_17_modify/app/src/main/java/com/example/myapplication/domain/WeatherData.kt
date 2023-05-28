package com.example.myapplication.domain

data class WeatherData(
    val weatherList: List<Weather>,
    val city: City,
    val position: Position
)