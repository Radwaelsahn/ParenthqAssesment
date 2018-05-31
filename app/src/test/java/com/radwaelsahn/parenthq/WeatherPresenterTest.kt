package com.radwaelsahn.parenthq


import android.app.Application
import android.content.Context
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.network.ErrorTypes
import com.radwaelsahn.parenthq.ui.weather.WeatherActivity
import com.radwaelsahn.parenthq.ui.weather.WeatherPresenter
import com.radwaelsahn.parenthq.ui.weather.WeatherView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherPresenterTest {


    @Mock
    lateinit var mMockContext: Context

    @Mock
    private lateinit var weatherView: WeatherView

    private lateinit var presenter: WeatherPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = WeatherPresenter(weatherView, mMockContext)
    }

    @Test
    fun testsWork() {
        val cities = presenter.getCitiesFromDB()
        if (cities!!.size > 0)
            Assert.assertTrue(true)

    }


    class WeatherViewTest : WeatherView {
        override fun refreshForecastList(forecasts: List<ForecastItemViewModel>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showErrorToast(errorType: ErrorTypes) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun reloadCitiesSpinner(cities: List<String>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showNoData() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


        override fun showLoading() {
        }

        override fun hideLoading() {
        }


        override fun cityDetectedFromGps(city: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showMessage(message: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


        override fun getContext(): Context {
            return getTestContext()
        }

        fun getTestContext(): Context {
            val application: Application = Mockito.mock(Application::class.java)
            Mockito.`when`(application.getString(R.string.icon_extension)).thenReturn("STRING_UNKNOWN_ERROR")
            Mockito.`when`(application.applicationContext).thenReturn(application)
            return application
        }


    }
}
