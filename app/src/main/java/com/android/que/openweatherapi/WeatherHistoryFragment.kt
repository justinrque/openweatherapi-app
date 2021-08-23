package com.android.que.openweatherapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.que.openweatherapi.adapter.WeatherAdapter
import com.android.que.openweatherapi.database.DatabaseClass
import com.android.que.openweatherapi.database.WeatherEntity
import kotlinx.android.synthetic.main.fragment_weather_history.*


class WeatherHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater!!.inflate(R.layout.fragment_weather_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = Room.databaseBuilder(
            requireContext().applicationContext,
            DatabaseClass::class.java,
            "weather_database"
        ).allowMainThreadQueries().build()

        val weatherData = database.weatherDao().getWeatherData()
        rv_weather_history.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = WeatherAdapter(weatherData as MutableList<WeatherEntity>)
        }
        if (isLoggedIn) {
            rv_weather_history.visibility = View.VISIBLE
        } else {
            rv_weather_history.visibility = View.INVISIBLE
            Toast.makeText(context, "Login to See History", Toast.LENGTH_SHORT).show()
        }
    }
}