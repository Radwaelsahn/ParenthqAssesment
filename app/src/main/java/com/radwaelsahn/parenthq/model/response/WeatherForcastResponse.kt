package com.radwaelsahn.parenthq.model.response

import com.radwaelsahn.parenthq.model.*

class WeatherForcastResponse {
    var message: String? = null

    var cnt: String? = null

    var cod: String? = null

    var list: List<Forcast>? = null

    var city: City? = null


    override fun toString(): String {
        return "ClassPojo [message = $message, cnt = $cnt, cod = $cod, list = $list, city = $city]"
    }
}



