package com.es.developine.network

object Endpoints {

    const val currentWeather = "http://api.openweathermap.org/data/2.5/weather?q=Cairo,eg&APPID=0791df36b036c61994c19f1978cdbf17"

    //    const val weatherForecast5Days1 = "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid=b1b15e88fa797225412429c1c50c122a1"
    const val weatherForecastEvery3Hours = "http://api.openweathermap.org/data/2.5/forecast?q=cairo,eg&mode=json&APPID=0791df36b036c61994c19f1978cdbf17"
    const val weatherForecast = "http://api.openweathermap.org/data/2.5/forecast/daily?q=cairo&appid=886705b4c1182eb1c69f28eb8c520e20&cnt=5"
    //"https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&appid=b1b15e88fa797225412429c1c50c122a1"
    const val iconUrl = "http://openweathermap.org/img/w/"


//    https://openweathermap.desk.com/customer/portal/questions/17136552-api-not-allowing-daily-forecast
//    the 16 days api is only working for paid version

}