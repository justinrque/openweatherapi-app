package com.android.que.openweatherapi.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    //KELVIN
//    https://api.openweathermap.org/data/2.5/weather?q=manila&appid=YOURAPIKEY
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") name: String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>

    //FAHRENHEIT
    //    https://api.openweathermap.org/data/2.5/weather?q=manila&units=imperial&appid=YOURAPIKEY
    @GET("data/2.5/weather?")
    fun getCurrentWeatherDataInFahrenheit(
        @Query("q") name: String,
        @Query("units") units: String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>


    //CELSIUS
//    https://api.openweathermap.org/data/2.5/weather?q=manila&units=metric&appid=YOURAPIKEY
    @GET("data/2.5/weather?")
    fun getCurrentWeatherDataInCelsius(
        @Query("q") name: String,
        @Query("units") units: String,
        @Query("appid") app_id: String
    ): Call<WeatherResponse>
}