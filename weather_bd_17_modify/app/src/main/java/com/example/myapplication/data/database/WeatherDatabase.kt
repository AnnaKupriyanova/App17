package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class, CityEntity::class, PositionEntity::class], version = 4)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
}