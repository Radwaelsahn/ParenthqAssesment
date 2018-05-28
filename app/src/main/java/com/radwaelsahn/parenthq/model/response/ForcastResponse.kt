package com.radwaelsahn.parenthq.model.response

import com.google.gson.annotations.SerializedName
import com.radwaelsahn.parenthq.model.Forcast

data class ForcastResponse(@SerializedName("city") var city: City,
                           @SerializedName("list") var forecast: List<Forcast>)

data class City(
        var coord: Coord? = null, var id: String? = null, var name: String? = null, var country: String? = null
)

data class Coord(
        var lon: String? = null, var lat: String? = null
)
