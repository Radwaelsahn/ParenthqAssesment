package com.radwaelsahn.parenthq.network

import com.radwaelsahn.parenthq.model.response.ForcastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Radwa Elsahn on 24.5.18
 */
interface OpenWeatherAPI {

    @GET("forecast/daily")
    fun dailyForecast(@Query("q") cityName : String, @Query("cnt") dayCount : Int) : Call<ForcastResponse>

    companion object {
        val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }
}