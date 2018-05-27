package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.location.Location
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.model.City

interface WeatherContract {

    fun getWeatherForcast()
    fun getWeatherForcastforCity(city: String,count:Int)
    fun loadCities(applicationContext: Context): List<City>
    fun getAddress(location: Location?, context: Context): String
}