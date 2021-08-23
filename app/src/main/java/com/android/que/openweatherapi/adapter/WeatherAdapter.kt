package com.android.que.openweatherapi.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.que.openweatherapi.R
import com.android.que.openweatherapi.database.DatabaseClass
import com.android.que.openweatherapi.database.WeatherEntity
import kotlinx.android.synthetic.main.item_layout.view.*

class WeatherAdapter(private val weatherData: MutableList<WeatherEntity>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tv_item_temperature.text = weatherData[position].temperature
        holder.view.tv_item_location.text = weatherData[position].location
        holder.view.tv_item_sunset.text = weatherData[position].sunset

        holder.itemView.setOnClickListener(View.OnClickListener {
            val database =
                Room.databaseBuilder(it.context, DatabaseClass::class.java, "weather-database")
                    .allowMainThreadQueries().build()

            var builder = AlertDialog.Builder(it.context)
            builder.setTitle("Confirm Deletion")
            builder.setMessage("Delete this item?")

            builder.setPositiveButton("Yes") { dialog, id ->
                database.weatherDao().delete(weatherData[position])
                weatherData.remove(weatherData[holder.adapterPosition])
                notifyDataSetChanged()
            }

            builder.setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }

            var alert = builder.create()
            alert.show()

        })

    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }
}