package com.radwaelsahn.parenthq.model


import com.google.gson.annotations.SerializedName

/**
 * Created by Radwa Elsahn on 24.5.18
 */
data class WeatherDescription(@SerializedName("main") var main : String,
                              @SerializedName("description") var description: String,
                              @SerializedName("icon") var icon: String)