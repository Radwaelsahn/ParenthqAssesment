package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.es.developine.network.Endpoints
import com.radwaelsahn.parenthq.R
import com.radwaelsahn.parenthq.getDate
import com.radwaelsahn.parenthq.model.Forcast
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.model.Weather
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*


class WeatherAdapter( val context: Context) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    var forecastList = mutableListOf<ForecastItemViewModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false))
    }

    fun addForecast(list: List<ForecastItemViewModel>) {
        forecastList.clear()
        forecastList.addAll(list)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position < forecastList.size) {
            val weatherItem: ForecastItemViewModel = forecastList.get(position)
            holder?.itemView.descriptionText.text = weatherItem.description
            holder?.itemView.dateText.text = weatherItem.date.toString()

            Picasso.with(holder.itemView.context)
                    .load("http://openweathermap.org/img/w/${weatherItem.icon}.png")
                    .into(holder.itemView.icon)
            holder.itemView.maxTemperature.text = "${weatherItem.degreeMax}º"
            holder.itemView.minTemperature.text = "${weatherItem.degreeMin}º"
            //holder?.itemView.setOnClickListener { itemClick(this) }


        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(forecastElement: ForecastItemViewModel) {

//            itemView.descriptionText.text = forecastElement.weather!!.get(0).description
//            itemView.dateText.text = weatherItem.dt_txt
//
//            Picasso.with(holder?.itemView.context)
//                    .load("${Endpoints.iconUrl}${weatherItem.weather!!.get(0).icon}.png")
////                            Endpoints.iconUrl + weatherItem.weather!!.get(0).icon + context.getString(R.string.icon_extension))
//                    .into(itemView.icon)
//            itemView.maxTemperature.text = "${weatherItem.main!!.temp_max}º"
//            itemView.minTemperature.text = "${weatherItem.main!!.temp_min}º"
//            itemView.setOnClickListener { itemClick(this) }


            //itemView.degreeText.text = "${forecastElement.degreeDay} °C ${forecastElement.description}"
//            itemView.dateText.text = getDate(forecastElement.date)

        }

        private fun itemClick(weatherAdapter: WeatherAdapter) {
            Log.i("a", "clicked");

        }
    }


}
