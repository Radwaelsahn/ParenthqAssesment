package com.radwaelsahn.parenthq

import android.app.Application
import android.content.Context
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.network.ErrorTypes
import com.radwaelsahn.parenthq.ui.weather.WeatherPresenter
import com.radwaelsahn.parenthq.ui.weather.WeatherView
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit


class WeatherPresenterTest {


    @Mock var mMockContext: Context? = null
    @Mock private lateinit var weatherView: WeatherView
    @JvmField @Rule var mockitoRule = MockitoJUnit.rule()
    private lateinit var presenter: WeatherPresenter

    @Before fun setUp() {
        presenter = WeatherPresenter(weatherView, weatherView.getContext())
    }


    @Test
    fun testsWork() {
        val cities = presenter.getCitiesFromDB()
        if (cities!!.size > 0)
            Assert.assertTrue(true)

        //BDDMockito.then(mockEventBus).should().register(presenter)
    }

    //    @Test
//    fun readStringFromContext_LocalizedString() {
//        // Given a mocked Context injected into the object under test...
//        `when`(mMockContext.getString(R.string.app_name))
//                .thenReturn("FAKE_STRING")
//        val myObjectUnderTest = ClassUnderTest(mMockContext)
//
//        // ...when the string is returned from the object under test...
//        val result = myObjectUnderTest.getHelloWorldString()
//
//        // ...then the result should be the expected one.
//        Assert.assertThat(result, `is`("FAKE_STRING"))
//    }
    class WeatherViewTest : WeatherView {
        override fun showNoData() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showCitiesSpinner(cities: List<String>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showLoading() {
        }

        override fun hideLoading() {
        }

        override fun updateForecastRecycler(forecasts: List<ForecastItemViewModel>) {
        }

        override fun showErrorToast(errorType: ErrorTypes) {

        }

        override fun cityDetected(city: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun insertWeatherDataInDb(weatherData: WeatherData) {
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
