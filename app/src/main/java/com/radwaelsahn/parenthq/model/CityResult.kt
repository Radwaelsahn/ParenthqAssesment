package com.radwaelsahn.parenthq.model

class CityResult {
    lateinit var woeid : String
    lateinit var cityName:String
    lateinit var country:String
    constructor() {}
    constructor(woeid:String, cityName:String, country:String) {
        this.woeid = woeid
        this.cityName = cityName
        this.country = country
    }
    public override fun toString():String {
        return cityName + "," + country
    }
}