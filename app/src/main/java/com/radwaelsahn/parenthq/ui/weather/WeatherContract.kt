package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.location.Location
import com.radwaelsahn.parenthq.model.response.City

interface WeatherContract {

    fun getWeatherForcastforCity(city: String, count: Int)
    fun getCitiesFromDB():List<String>
    fun returnCities(): List<String>
    fun getAddress(location: Location?, context: Context): String
    fun onDestroy()
    fun loadCities(): List<City>
    fun getForcastFromDB(cityName: String)
    fun clearDataFromDB()
}