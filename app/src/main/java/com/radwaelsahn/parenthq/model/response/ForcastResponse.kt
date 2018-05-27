package com.radwaelsahn.parenthq.model.response

import com.google.gson.annotations.SerializedName
import com.radwaelsahn.parenthq.model.City
import com.radwaelsahn.parenthq.model.Forcast

data class ForcastResponse (@SerializedName("city") var city : City,
                            @SerializedName("list") var forecast : List<Forcast>)