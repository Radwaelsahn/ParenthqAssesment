package com.radwaelsahn.parenthq.model

import com.radwaelsahn.parenthq.model.response.WeatherForcastResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


data class ForecastItemViewModel(val degreeMax: String,
                                 val degreeMin: String,
                                 val icon: String = "01d",
                                 val date: String,
                                 val description: String = "No description",
                                 val city: String)


//fun convertToDomain(weatherResponse: WeatherForcastResponse) {
//    var list: List<Forecast>
//    var forcast:
//            dateTime = LocalDate.parse("Thursday, January 25, 2018", DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
//}

