package com.radwaelsahn.parenthq.model


class ForcastList {
    val clouds: Clouds? = null

    val dt: String? = null

    val wind: Wind? = null

    val sys: Sys? = null

    val weather: Array<Weather>? = null

    val dt_txt: String? = null

    val main: Main? = null

    override fun toString(): String {
        return "Class [clouds = $clouds, dt = $dt, wind = $wind, sys = $sys, weather = $weather, dt_txt = $dt_txt, main = $main]"
    }

//    fun convretToElement(forcast: Forcast): List<ForecastItemViewModel> {
//        var list: List<ForecastItemViewModel>
//        var forecastElement: ForecastItemViewModel
//
//        forecastElement.date= getDate(forcast.dt)
//        forecastElement.degreeDay=
//            return forecastElement;
//    }
}
