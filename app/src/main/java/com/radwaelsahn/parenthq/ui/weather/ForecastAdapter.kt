package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radwaelsahn.parenthq.R
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.forecast_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(val context: Context) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    var forecastList = mutableListOf<ForecastItemViewModel>()

    fun addForecast(list: List<ForecastItemViewModel>) {
        forecastList.clear()
        forecastList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        forecastList[position].let {
            holder.bind(forecastElement = it)
        }
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(forecastElement: ForecastItemViewModel) {
            itemView.degreeText.text = "${forecastElement.degreeMax} °C ${forecastElement.description}"
            itemView.dateText.text = forecastElement.date
            Log.i("image", "http://openweathermap.org/img/w/${forecastElement.icon}.png");
            Picasso.with(itemView.context)
                    .load("http://openweathermap.org/img/w/${forecastElement.icon}.png")
        }


    }

}