package com.radwaelsahn.parenthq.model.response

import com.radwaelsahn.parenthq.model.City
import com.radwaelsahn.parenthq.model.Forcast

class Weather5DaysResponse {
    var message: String? = null

    var cnt: String? = null

    var cod: String? = null

    var list: List<Forcast>? = null

    var city: City? = null
}
