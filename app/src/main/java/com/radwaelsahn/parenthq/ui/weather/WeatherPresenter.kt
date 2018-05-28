package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.extensions.readJSONfromFile
import com.radwaelsahn.parenthq.model.City
import com.radwaelsahn.parenthq.model.Forcast
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.model.response.ForcastResponse
import com.radwaelsahn.parenthq.network.ErrorTypes
import com.radwaelsahn.parenthq.network.OpenWeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherPresenter(var weatherView: WeatherView, applicationContext: Context) : WeatherContract {

    @Inject
    lateinit var api: OpenWeatherAPI

    lateinit var weatherInteractor: WeatherInteractor
    lateinit var cities: List<String>
    private val mApplicationContext: Context

    init {
        mApplicationContext = applicationContext
        weatherInteractor = WeatherInteractor(applicationContext)
    }


    override fun getWeatherForcastforCity(cityName: String, count: Int) {

        weatherView.showLoading()
        api.dailyForecast(cityName, count).enqueue(object : Callback<ForcastResponse> {

            override fun onResponse(call: Call<ForcastResponse>, response: Response<ForcastResponse>) {
                Log.i("response", response.toString())
                weatherView.hideLoading()
                response.body()?.let {
                    createListForView(it, cityName)
                } ?: weatherView.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<ForcastResponse>?, t: Throwable) {
                Log.i("error", call.toString())
                weatherView.hideLoading()
                weatherView.showErrorToast(ErrorTypes.CONNECTION_ERROR)
                t.printStackTrace()
            }
        })
    }

    override fun loadCities(): List<City> {
        val citiesJson = readJSONfromFile("cities.json", mApplicationContext)
        var gson = Gson()
        var cities = gson.fromJson<List<City>>(citiesJson, object : TypeToken<List<City>>() {}.type)

        return cities
    }

    override fun getCitiesFromDB(): List<String> {
        return weatherInteractor.getCitiesFromDB(this)
    }

    override fun getForcastFromDB(cityName: String) {
        weatherInteractor.getWeatherDataFromDb(this, cityName)
    }


    fun updateCitiesUI(citiesDb: List<String>) {
        cities = citiesDb
        for (city in cities)
            Log.i("city", city)

        weatherView.showCitiesSpinner(cities)

        Log.i("radwa", "updateCities")
        //Log.i("cities4", "result" + citiesDb!!.size.toString())


    }

    override fun returnCities(): List<String> {
        return cities
    }


    private fun createListForView(weatherResponse: ForcastResponse, cityName: String) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for (forecastDetail: Forcast in weatherResponse.forecast) {
            val dayTemp = forecastDetail.temperature.dayTemperature
            val nitTemp = forecastDetail.temperature.nightTemperature
            val date = getDate(forecastDetail.date)
            val forecastItem = ForecastItemViewModel(degreeMax = dayTemp.toString(),
                    degreeMin = nitTemp.toString(),
                    date = date,
                    icon = forecastDetail.description[0].icon,
                    description = forecastDetail.description[0].description, city = cityName)
            forecasts.add(forecastItem)

            val forecastEntityItem = WeatherData(degreeMax = dayTemp.toString(),
                    degreeMin = nitTemp.toString(),
                    date = date,
                    icon = forecastDetail.description[0].icon,
                    description = forecastDetail.description[0].description, city = cityName)

            weatherView.insertWeatherDataInDb(forecastEntityItem)

        }
        weatherView.updateForecastRecycler(forecasts)

    }

    fun convertToViewModel(weatherDataList: List<WeatherData>, cityName: String) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for (forecastDetail: WeatherData in weatherDataList) {
            val forecastItem = ForecastItemViewModel(degreeMax = forecastDetail.degreeMax,
                    degreeMin = forecastDetail.degreeMin,
                    date = forecastDetail.date,
                    icon = forecastDetail.icon,
                    description = forecastDetail.description, city = cityName)
            forecasts.add(forecastItem)
        }

        weatherView.updateForecastRecycler(forecasts)
    }

    private fun getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date * 1000L))
    }

    override fun getAddress(location: Location?, context: Context): String {

        var geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>

        addresses = geocoder.getFromLocation(location!!.latitude, location!!.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        var city: String = addresses.get(0).adminArea;

        if (city.contains(" Governorate"))
            city = city.removeSuffix(" Governorate")

        Log.i("CITY", city)
        weatherView.cityDetected(city)

        return city
    }

    fun showNoData() {
        weatherView.showNoData()
    }

    override fun onDestroy() {
        weatherInteractor.onDestroy()
    }
}