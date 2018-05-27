package com.radwaelsahn.parenthq.data.db.Entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.radwaelsahn.parenthq.model.City


@Entity(tableName = "weatherData" ,primaryKeys = arrayOf("date", "city"))
data class WeatherData(
//(@PrimaryKey(autoGenerate = true) var id: Long?,
                       @ColumnInfo(name = "degreeMax") var degreeMax: String,
                       @ColumnInfo(name = "degreeMin") var degreeMin: String,
                       @ColumnInfo(name = "icon") var icon: String,
                       @ColumnInfo(name = "date") var date: String,
                       @ColumnInfo(name = "description") var description: String,
                       @ColumnInfo(name = "city") var city: String
) {
    constructor() : this(
            "", "", "", "", "", "")
}