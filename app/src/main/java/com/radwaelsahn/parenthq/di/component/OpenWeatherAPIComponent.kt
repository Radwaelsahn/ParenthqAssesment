package com.radwaelsahn.parenthq.di.component

import com.radwaelsahn.parenthq.ApplicationClass
import com.radwaelsahn.parenthq.di.module.OpenWeatherAPIModule
import com.radwaelsahn.parenthq.ui.weather.WeatherActivity
import com.radwaelsahn.parenthq.ui.weather.WeatherPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(OpenWeatherAPIModule::class))
interface OpenWeatherAPIComponent {
    fun inject(presenter: WeatherPresenter);
    fun inject(mewApplication: ApplicationClass)
    fun inject(weatherActivity: WeatherActivity)
}
