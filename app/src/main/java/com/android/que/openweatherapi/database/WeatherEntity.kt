package com.android.que.openweatherapi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "temperature") val temperature: String?,
    @ColumnInfo(name = "sunset") val sunset: String?
)