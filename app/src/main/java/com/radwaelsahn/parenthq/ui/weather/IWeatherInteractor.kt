package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.os.Handler
import android.util.Log
import com.radwaelsahn.parenthq.data.db.DbWorkerThread
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.data.db.WeatherDataBase


interface IWeatherInteractor{

    fun initDBResources(applicationContext: Context)
    fun getCitiesFromDB(weatherPresenter: WeatherPresenter): List<String>
    fun getWeatherDataFromDb(weatherPresenter: WeatherPresenter, cityName: String)
    fun onDestroy()
    fun insertWeatherDataInDb(weatherData: WeatherData)
    fun clearData()
}