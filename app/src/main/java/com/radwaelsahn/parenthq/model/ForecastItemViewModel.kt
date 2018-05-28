package com.radwaelsahn.parenthq.model


data class ForecastItemViewModel(val degreeMax: String,
                                 val degreeMin: String,
                                 val icon: String = "01d",
                                 val date: String,
                                 val description: String = "No description",
                                 val city: String)


