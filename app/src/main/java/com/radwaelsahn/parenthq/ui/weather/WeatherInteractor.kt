package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.os.Handler
import android.util.Log
import com.radwaelsahn.parenthq.data.db.DbWorkerThread
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.data.db.WeatherDataBase


class WeatherInteractor(applicationContext: Context) : IWeatherInteractor {
    private var mDb: WeatherDataBase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()


    init {
        initDBResources(applicationContext)
    }

    override fun initDBResources(applicationContext: Context) {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDb = WeatherDataBase.getInstance(applicationContext)
    }


    override fun getCitiesFromDB(weatherPresenter: WeatherPresenter): List<String> {
        val task = Runnable {
            val citiesData =
                    mDb?.weatherDataDao()?.getCities()
            mUiHandler.post({
                if (citiesData != null && citiesData?.size > 0) {
                    Log.i("citiesData1", citiesData.size.toString())
                    weatherPresenter.updateCitiesUI(citiesData)
                }
            })
        }
        mDbWorkerThread.postTask(task)

        return emptyList()

    }

    override fun getWeatherDataFromDb(weatherPresenter: WeatherPresenter, cityName: String) {

        val task = Runnable {
            val weatherData =
                    mDb?.weatherDataDao()?.getCityForecast(cityName = cityName)
            mUiHandler.post({
                if (weatherData == null || weatherData?.size == 0) {
                    weatherPresenter.showNoData()
                } else {
                    weatherPresenter.convertToViewModel(weatherData, cityName)
                }
            })
        }
        mDbWorkerThread.postTask(task)

    }


    override fun onDestroy() {
        if (WeatherDataBase != null) {
            WeatherDataBase.destroyInstance()
            mDbWorkerThread.quit()
        }
    }

    override fun insertWeatherDataInDb(weatherData: WeatherData) {
        val task = Runnable { mDb?.weatherDataDao()?.insert(weatherData) }
        mDbWorkerThread.postTask(task)
    }

    override fun clearData() {
        val task = Runnable { mDb?.weatherDataDao()?.deleteAll() }
        mDbWorkerThread.postTask(task)
    }
}