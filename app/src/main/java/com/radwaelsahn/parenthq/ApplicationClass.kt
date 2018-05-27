package com.radwaelsahn.parenthq

import android.app.Application
import com.radwaelsahn.parenthq.di.component.DaggerOpenWeatherAPIComponent
import com.radwaelsahn.parenthq.di.component.OpenWeatherAPIComponent
import com.radwaelsahn.parenthq.di.module.OpenWeatherAPIModule

/**
 * Created by RadwaElsahn on 23/05/2018.
 */
open class ApplicationClass : Application() {

    public lateinit var applicationComponent: OpenWeatherAPIComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerOpenWeatherAPIComponent.builder()
                .openWeatherAPIModule(OpenWeatherAPIModule())
                .build()

        applicationComponent.inject(this)
    }
}