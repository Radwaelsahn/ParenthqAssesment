package com.radwaelsahn.parenthq.model

class City {

    var coord: Coord? = null

    var id: String? = null

    var name: String? = null

//    var population: String? = null

    var country: String? = null

    override fun toString(): String {
        return name.toString()
        //"ClassPojo [coord = $coord, id = $id, name = $name, country = $country]"
    }

    fun indexOf(cities: List<City>, cityName: String): Int {
        for (i in 0..cities.size) {
            if (cities.get(i).name.equals(cityName,true)) {
                return i
            }
        }
        return -1
    }
}
