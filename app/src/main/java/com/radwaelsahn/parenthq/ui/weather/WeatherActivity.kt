package com.radwaelsahn.parenthq.ui.weather

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

import android.os.Bundle
import android.provider.Settings

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.radwaelsahn.parenthq.BuildConfig
import com.radwaelsahn.parenthq.R
import com.radwaelsahn.parenthq.di.component.DaggerOpenWeatherAPIComponent
import com.radwaelsahn.parenthq.di.module.OpenWeatherAPIModule
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.network.ErrorTypes
import com.radwaelsahn.parenthq.utils.REQUEST_PERMISSIONS_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*

class WeatherActivity : AppCompatActivity(), WeatherView {

    lateinit var weatherPresenter: WeatherPresenter
    var cities = emptyList<String>()
    private val TAG = "MainActivity"
    lateinit var locationManager: com.radwaelsahn.parenthq.ui.weather.LocationManager

//    private var mDb: WeatherDataBase? = null
//    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        setContentView(R.layout.activity_main)
        initialize()

        weatherPresenter.getCitiesFromDB()
    }


    override fun hideLoading() {
        forecastRecyclerView.visibility = View.VISIBLE
        loadingSpinner.visibility = View.GONE
    }

    override fun showLoading() {
        forecastRecyclerView.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.VISIBLE
    }


    override fun showErrorToast(errorType: ErrorTypes) {
        Toast.makeText(this, errorType.name, Toast.LENGTH_LONG).show()
        forecastRecyclerView.visibility = View.GONE
    }


    private fun injectDI() {
        weatherPresenter = WeatherPresenter(this, application)
        DaggerOpenWeatherAPIComponent
                .builder()
                .openWeatherAPIModule(OpenWeatherAPIModule())
                .build()
                .inject(weatherPresenter)
    }


    override fun refreshForecastList(forecasts: List<ForecastItemViewModel>) {

        if (forecasts.isEmpty()) emptyStateText.visibility = View.VISIBLE
        else forecastRecyclerView.visibility = View.VISIBLE
        forecastRecyclerView.adapter.safeCast<WeatherAdapter>()?.addForecast(forecasts)
    }

    override fun reloadCitiesSpinner(cities: List<String>) {
        Log.i("radwa", "updateCitiesUI")
        if (cities.size > 0) {
            layout_cities.setVisibility(View.VISIBLE)
            cities_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
            var selectedIndx: Int = cities.indexOf(weatherPresenter.selectedCity)
            if (selectedIndx != -1)
                cities_spinner.setSelection(selectedIndx)
        } else
            layout_cities.setVisibility(View.GONE)

    }

    inline fun <reified T> Any.safeCast() = this as? T

    private fun initialize() {

        if (weatherPresenter.selectedCity.isNullOrEmpty())
            locationManager = com.radwaelsahn.parenthq.ui.weather.LocationManager(weatherPresenter, this)

        forecastRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WeatherAdapter(applicationContext)
        }

        cities_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
        cities_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.i("radwa", "itemSelected")
                weatherPresenter.getForecast(cities_spinner.adapter.getItem(position).toString(), 5)
            }
        }

        btn_remove_cities.setOnClickListener { clearCities() }
    }

    private fun clearCities() {
        weatherPresenter.clearDataFromDB()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_page_menu, menu)

        val menuItem = menu?.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if (searchMenuItem is android.support.v7.widget.SearchView) {
            searchMenuItem.queryHint = getString(R.string.menu_search_hint)
            searchMenuItem.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    weatherPresenter.getForecast(query, 5)
                    menuItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        return true
    }


    override fun onStart() {
        super.onStart();
        locationManager.onStart()

    }

    override fun onStop() {
        super.onStop();
        locationManager.onStop()
    }


    override fun cityDetectedFromGps(city: String) {
        if (!city.isNullOrEmpty()) {
            //var indx: Int = City().indexOf(cities, "aswan")//selectedCity)
            if (weatherPresenter.returnCities() != null) {
                var indx = weatherPresenter.returnCities().indexOf(city)
                if (indx > -1)
                    cities_spinner.setSelection(indx)
            }

        }
    }

    override fun showNoData() {
        showMessage("No data in cache..!!")
        forecastRecyclerView.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()

        weatherPresenter.onDestroy()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return applicationContext
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//Permission granted.
                locationManager.getLastLocation()
            } else { // Permission denied. // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.
                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask // again" prompts). Therefore, a user interface
                // affordance is typically implemented // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.


//                showSnackbar("permission_denied_explanation", "settings", View.OnClickListener {
//                    // Build intent that displays the App settings screen.
//                    val intent = Intent()
//                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                    val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
//                    intent.data = uri
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    applicationContext.startActivity(intent)
//                })
            }
        }

    }
}
