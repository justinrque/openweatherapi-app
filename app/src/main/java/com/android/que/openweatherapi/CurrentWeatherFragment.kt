package com.android.que.openweatherapi

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.android.que.openweatherapi.Data.Companion.API_KEY
import com.android.que.openweatherapi.Data.Companion.BASE_URL
import com.android.que.openweatherapi.Data.Companion.CITY_NAME
import com.android.que.openweatherapi.database.DatabaseClass
import com.android.que.openweatherapi.database.WeatherEntity
import com.android.que.openweatherapi.network.WeatherApiService
import com.android.que.openweatherapi.network.WeatherResponse
import kotlinx.android.synthetic.main.fragment_current_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


class CurrentWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater!!.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentWeatherData()
    }

    private fun getCurrentWeatherData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(WeatherApiService::class.java)
        val call = retrofitService.getCurrentWeatherData(
            CITY_NAME,
            API_KEY
        )
//        val call = retrofitService.getCurrentWeatherDataInCelsius(CITY_NAME, CELSIUS, API_KEY)
        call.enqueue(object : Callback<WeatherResponse> {
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                println(response.code())
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    tv_location.text = weatherResponse.name + ", " + weatherResponse.sys.country

                    val kelvin = weatherResponse.main.temp
                    val celsius = kelvin - 273.15
                    val roundedCelsius = String.format("%.2f", celsius)
                    tv_temperature.text = "$roundedCelsius°C"

                    val sunriseSdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    sunriseSdf.timeZone = TimeZone.getTimeZone("GMT+8")
                    tv_sunrise.text = sunriseSdf.format(Date(weatherResponse.sys.sunrise * 1000))

                    val sunsetSdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    sunsetSdf.timeZone = TimeZone.getTimeZone("GMT+8")
                    tv_sunset.text = sunsetSdf.format(Date(weatherResponse.sys.sunset * 1000))


                    val z = ZoneId.of("Asia/Manila")
                    Log.d("Time now: ", LocalTime.now(z).toString())
                    Log.d("Fetched Data: ", weatherResponse.weather[0].toString())

                    when {
                        weatherResponse.weather[0].toString().contains("Rain") -> {
                            imageView.setImageResource(R.drawable.rain)
                        }
                        LocalTime.now(z).isAfter(LocalTime.parse("18:00")) -> {
                            imageView.setImageResource(R.drawable.moon)
                        }
                        else -> {
                            imageView.setImageResource(R.drawable.sun)
                        }
                    }

                    var fahrenheit = String.format("%.2f", (kelvin - 273.15) * 1.8 + 32)
                    Log.d("Fahrenheit: ", fahrenheit)
                    var itemTemperature = "$fahrenheit °F"
                    var itemLocation: String =
                        weatherResponse.name + ", " + weatherResponse.sys.country
                    var itemSunset: String = tv_sunset.text as String

                    val database = Room.databaseBuilder(
                        requireContext().applicationContext,
                        DatabaseClass::class.java,
                        "weather_database"
                    ).allowMainThreadQueries().build()
                    if (isLoggedIn) {
                        database.weatherDao().insertWeatherData(
                            WeatherEntity(
                                location = itemLocation,
                                temperature = itemTemperature,
                                sunset = itemSunset
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(context, "An error occurred. " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}