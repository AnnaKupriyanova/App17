package com.example.myapplication.ui

import com.example.myapplication.domain.City
import com.example.myapplication.domain.Weather
import com.example.myapplication.ui.dataclass.CityUI
import com.example.myapplication.ui.dataclass.WeatherUI

internal fun City.toUI(): CityUI = CityUI(cityName = cityName)

internal fun List<Weather>.toUI(): List<WeatherUI> = map {
    it.toUI()
}

internal fun Weather.toUI(): WeatherUI = WeatherUI(
    dtTxt = dtTxt, temp = temp, pressure = pressure, icon = icon
)