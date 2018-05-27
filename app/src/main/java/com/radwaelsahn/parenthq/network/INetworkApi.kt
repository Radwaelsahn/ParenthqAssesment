package com.es.developine.network

import android.telecom.Call
import com.radwaelsahn.parenthq.BuildConfig
import com.radwaelsahn.parenthq.model.response.CurrentWeatherResponse
import com.radwaelsahn.parenthq.model.response.WeatherForcastResponse
import io.reactivex.Observable
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkApi {

    @GET(Endpoints.currentWeather)
    fun getCurrentWeather(): Observable<CurrentWeatherResponse>

    @GET(Endpoints.weatherForecastEvery3Hours)
    fun getWeather5Days(): Observable<WeatherForcastResponse>

    @GET(Endpoints.weatherForecast)
    fun dailyForecast(): Observable<WeatherForcastResponse>
    //fun dailyForecast(city: String, count: Int): Observable<WeatherForcastResponse>


//    @GET("forecast/daily/")
//    fun dailyForecast(@Query("q") cityName : String, @Query("cnt") dayCount : Int) : Call<WeatherForcastResponse>

//    companion object {
//        val BASE_URL = "http://api.openweathermap.org/data/2.5/"
//    }

}

//class OpenWeatherInterceptor : Interceptor{
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val url: HttpUrl = chain.request().url().newBuilder().addQueryParameter("APPID", BuildConfig.OPEN_WEATHER_API_KEY).
//                addQueryParameter("mode", "json").
//                addQueryParameter("units", "metric").build()
//        return chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build())
//    }
//
//}