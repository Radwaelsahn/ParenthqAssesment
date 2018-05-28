package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.model.response.ForcastResponse
import com.radwaelsahn.parenthq.model.response.WeatherForcastResponse
import com.radwaelsahn.parenthq.network.ErrorTypes

interface WeatherView {
    fun getContext(): Context
    fun showLoading()
    fun hideLoading()
    fun showNoData()
    fun updateUI(forecasts: List<ForecastItemViewModel>)
    fun updateCitiesUI(cities: List<String>)
    fun showErrorToast(errorType: ErrorTypes)
    fun cityDetected(city: String)
    fun insertWeatherDataInDb(weatherData: WeatherData)
    fun showMessage(message: String)
}