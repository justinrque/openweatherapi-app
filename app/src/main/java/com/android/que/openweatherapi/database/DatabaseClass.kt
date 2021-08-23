package com.android.que.openweatherapi.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1)
abstract class DatabaseClass : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}