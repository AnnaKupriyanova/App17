package com.example.myapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date_time")
    val dtTxt: String,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "icon")
    val icon: String
)

@Entity
class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "city_name")
    val cityName: String
)

@Entity
class PositionEntity(
    @PrimaryKey
    @ColumnInfo(name = "position")
    val lat: String,
    val lon: String
)