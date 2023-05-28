package com.example.myapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WeatherDao {
    @Insert
    suspend fun putWeather(weather: List<WeatherEntity>)

    @Query("DELETE FROM WeatherEntity")
    suspend fun clearWeather()

    @Query("SELECT * FROM WeatherEntity")
    suspend fun getWeather(): List<WeatherEntity>

    @Transaction
    suspend fun updateWeather(weather: List<WeatherEntity>, city: CityEntity, position: PositionEntity) {
        clearAllTables()
        putWeather(weather)
        putCity(city)
        putPosition(position)
    }

    @Transaction
    suspend fun WeatherDao.clearAllTables() {
        clearWeather()
        clearCity()
        clearPosition()
    }

    @Insert
    suspend fun putCity(cityEntity: CityEntity)

    @Query("SELECT * FROM CityEntity")
    suspend fun getCity(): CityEntity

    @Query("DELETE FROM CityEntity")
    suspend fun clearCity()

    @Insert
    suspend fun putPosition(positionEntity: PositionEntity)

    @Query("SELECT * FROM PositionEntity")
    suspend fun getPosition(): PositionEntity

    @Query("DELETE FROM PositionEntity")
    suspend fun clearPosition()
}