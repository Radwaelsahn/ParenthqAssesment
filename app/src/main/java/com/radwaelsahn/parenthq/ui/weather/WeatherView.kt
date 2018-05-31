package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.network.ErrorTypes

interface WeatherView {
    fun getContext(): Context
    fun showLoading()
    fun hideLoading()
    fun showNoData()
    fun refreshForecastList(forecasts: List<ForecastItemViewModel>)
    fun reloadCitiesSpinner(cities: List<String>)
    fun showErrorToast(errorType: ErrorTypes)
    fun cityDetectedFromGps(city: String)
    fun showMessage(message: String)
}