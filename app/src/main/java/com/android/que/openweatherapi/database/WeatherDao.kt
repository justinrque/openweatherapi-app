package com.android.que.openweatherapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    fun insertWeatherData(weatherEntity: WeatherEntity?)

    @get:Query("SELECT * FROM weatherEntity")
    val getWeatherData: List<WeatherEntity>?

    @Delete
    fun delete(weatherEntity: WeatherEntity?)
}

