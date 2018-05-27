package com.radwaelsahn.parenthq.data.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData

@Dao
interface WeatherDataDao {
    @Query("SELECT DISTINCT city from weatherData")
    fun getCities(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherData: WeatherData)

    @Query("DELETE from weatherData")
    fun deleteAll()

    @Query("SELECT * from weatherData where city = :cityName")
    fun getCityForecast(cityName: String): List<WeatherData>
}