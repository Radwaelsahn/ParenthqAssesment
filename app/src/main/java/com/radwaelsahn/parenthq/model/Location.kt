package com.radwaelsahn.parenthq.model

import java.io.Serializable

//class Location : Serializable {
//    var longitude: Float = 0.toFloat()
//    var latitude: Float = 0.toFloat()
//    var sunset: Long = 0
//    var sunrise: Long = 0
//    var country: String = ""
//    var city: String = ""
//}

data class Location (var longitude: Float ,var latitude: Float,var sunset:Long,var sunrise: Long,var country: String ,var city: String): Serializable