package com.radwaelsahn.parenthq.model

import com.google.gson.annotations.SerializedName


data class Forcast(@SerializedName("dt") var  date: Long,
                          @SerializedName("temp") var temperature : Temperature,
                          @SerializedName("weather") var description : List<WeatherDescription>,
                          @SerializedName("pressure") var pressure : Double,
                          @SerializedName("humidity") var humidity :Double)