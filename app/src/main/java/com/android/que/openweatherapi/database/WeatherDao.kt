package com.android.que.openweatherapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    fun insertWeatherData(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weatherEntity")
    fun getWeatherData(): List<WeatherEntity>

    @Query("DELETE FROM weatherEntity WHERE uid = :id")
    fun delete(id: Int)
}

