package com.example.myapplication.domain

data class Weather(
    val dtTxt: String,
    val temp: Double,
    val pressure: Int,
    val icon: String
)