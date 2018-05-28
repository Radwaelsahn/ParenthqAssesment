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
    fun updateForecastRecycler(forecasts: List<ForecastItemViewModel>)
    fun showErrorToast(errorType: ErrorTypes)
    fun cityDetected(city: String)
    fun insertWeatherDataInDb(weatherData: WeatherData)
    fun showMessage(message: String)
    fun showCitiesSpinner(cities:List<String>)


}