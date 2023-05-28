package com.example.myapplication.data

import com.example.myapplication.App
import com.example.myapplication.data.database.CityEntity
import com.example.myapplication.data.database.PositionEntity
import com.example.myapplication.data.database.WeatherEntity
import com.example.myapplication.data.network.WeatherNetwork
import com.example.myapplication.domain.City
import com.example.myapplication.domain.Position
import com.example.myapplication.domain.Weather
import com.example.myapplication.domain.WeatherData

internal fun List<WeatherNetwork.DataWeather>.toDomain(): List<Weather> = map {
    it.toDomain()
}

internal fun WeatherNetwork.DataWeather.toDomain(): Weather = Weather(
    dtTxt = this.dtTxt,
    temp = this.main.temp,
    pressure = this.main.pressure,
    icon = this.weather.first().icon
)

internal suspend fun WeatherNetwork.toDomain(): WeatherData = WeatherData(
    this.list.toDomain(),
    this.city.toDomain(),
    App.weatherDatabase.weatherDao().getPosition().toDomain()
)

internal fun WeatherNetwork.City.toDomain(): City = City(
    cityName = name
)

internal fun WeatherNetwork.City.toEntity(): CityEntity = CityEntity(
    cityName = name
)

internal fun List<WeatherNetwork.DataWeather>.toEntity(): List<WeatherEntity> = map {
    it.toEntity()
}

internal fun WeatherNetwork.DataWeather.toEntity(): WeatherEntity = WeatherEntity(
    dtTxt = this.dtTxt,
    temp = this.main.temp,
    pressure = this.main.pressure,
    icon = this.weather.first().icon
)

internal fun CityEntity.toDomain(): City = City(
    cityName = cityName
)

@JvmName("toDomainWeatherEntity")

internal fun List<WeatherEntity>.toDomain(): List<Weather> = map {
    it.toDomain()
}

internal fun WeatherEntity.toDomain(): Weather = Weather(
    dtTxt = dtTxt,
    temp = temp,
    pressure = pressure,
    icon = icon
)

fun PositionEntity.toDomain(): Position = Position(lat, lon)

fun Position.toEntity(): PositionEntity = PositionEntity(lat, lon)
