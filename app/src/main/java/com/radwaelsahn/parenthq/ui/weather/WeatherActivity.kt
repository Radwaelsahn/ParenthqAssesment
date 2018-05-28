package com.radwaelsahn.parenthq.ui.weather

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.multidex.MultiDex
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.radwaelsahn.parenthq.R
import com.radwaelsahn.parenthq.data.db.DbWorkerThread
import com.radwaelsahn.parenthq.data.db.Entities.WeatherData
import com.radwaelsahn.parenthq.data.db.WeatherDataBase
import com.radwaelsahn.parenthq.di.component.DaggerOpenWeatherAPIComponent
import com.radwaelsahn.parenthq.di.module.OpenWeatherAPIModule
import com.radwaelsahn.parenthq.extensions.isConnectedToInternet
import com.radwaelsahn.parenthq.model.City
import com.radwaelsahn.parenthq.model.ForecastItemViewModel
import com.radwaelsahn.parenthq.network.ErrorTypes
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by RadwaElsahn on 23/05/2018.
 */
class WeatherActivity : AppCompatActivity(), WeatherView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    lateinit var weatherPresenter: WeatherPresenter
    var cities = emptyList<String>()
    private val TAG = "MainActivity"
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location

    private var mDb: WeatherDataBase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        setContentView(R.layout.activity_main)
        initialize()
        callFirstForecast()

        weatherPresenter.getCitiesFromDB()

        //cities = weatherPresenter.loadCities()
//        showCitiesSpinner()

        //        Log.i("cities", weatherPresenter.returnCities().size.toString())
    }

    private fun callFirstForecast() {
        if (weatherPresenter.selectedCity.isNullOrEmpty())
            getLocation()
    }

    override fun onResume() {
        super.onResume()
        test()
    }


    private fun test() {
        //hello.setText("Hello world with kotlin extensions")
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
    }


    private fun injectDI() {
        weatherPresenter = WeatherPresenter(this, application)
        DaggerOpenWeatherAPIComponent
                .builder()
                .openWeatherAPIModule(OpenWeatherAPIModule())
                .build()
                .inject(weatherPresenter)
    }


    override fun updateUI(forecasts: List<ForecastItemViewModel>) {
//        Log.i("updateUI", "a")
        if (forecasts.isEmpty()) emptyStateText.visibility = View.VISIBLE
        else forecastRecyclerView.visibility = View.VISIBLE
        forecastRecyclerView.adapter.safeCast<WeatherAdapter>()?.addForecast(forecasts)
        //weatherPresenter.getCitiesFromDB()
    }

    override fun updateCitiesUI(cities: List<String>) {
        cities_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)

        //cities_spinner.adapter.notifyDataSetChanged()
    }

    //private fun getForecast(query: String, count: Int) = weatherPresenter.getWeatherForcastforCity(query, count)
    private fun getForecast(cityName: String, count: Int) {
        Log.i("getForcast", cityName)
        if (cityName.isNullOrEmpty()) {
            Toast.makeText(this, "please search for a city", Toast.LENGTH_SHORT).show();
            return
        }
        if (isConnectedToInternet())
            weatherPresenter.getWeatherForcastforCity(cityName, count)
        else
            weatherPresenter.getForcastFromDB(cityName)
        //getWeatherDataFromDb(this, cityName)
    }

    inline fun <reified T> Any.safeCast() = this as? T

    private fun initialize() {
        initDBResources(applicationContext)

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
                getForecast(cities_spinner.adapter.getItem(position).toString(), 5)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_page_menu, menu)

        val menuItem = menu?.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if (searchMenuItem is android.support.v7.widget.SearchView) {
            searchMenuItem.queryHint = getString(R.string.menu_search_hint)
            searchMenuItem.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    getForecast(query, 5)
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


    private fun getLocation() {
        MultiDex.install(this)

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = this.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager

        checkLocation()
    }

    override fun onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    override fun onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    override fun onConnectionSuspended(p0: Int) {

        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i("a", "onConnectionFailed")
        getForecast("London, UK", 5)
    }

    override fun onLocationChanged(location: Location) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getForecast("London, UK", 5)
            return;
        }

//        startLocationUpdates();

        var fusedLocationProviderClient:
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {

                        mLocation = location;
                        Log.i("radwa", "Location");

                        var city: String = weatherPresenter.getAddress(location, this)
                        getForecast(city, 5)

                    }
                })
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private fun isLocationEnabled(): Boolean {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    override fun cityDetected(city: String) {
        Log.i("radwa", "cityDetected:  ${city}")
        if (!city.isNullOrEmpty()) {
            //var indx: Int = City().indexOf(cities, "aswan")//selectedCity)
            if (weatherPresenter.returnCities() != null) {
                var indx = weatherPresenter.returnCities().indexOf(city)
                if (indx > -1)
                    cities_spinner.setSelection(indx)
                Log.i("radwa", "cityDetected:  ${city} ${indx}")
            }

        }
    }

    fun initDBResources(applicationContext: Context) {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDb = WeatherDataBase.getInstance(applicationContext)
    }

    override fun showNoData() {
        showMessage("No data in cache..!!")
        forecastRecyclerView.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.GONE
    }

    override fun insertWeatherDataInDb(weatherData: WeatherData) {
        val task = Runnable { mDb?.weatherDataDao()?.insert(weatherData) }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        super.onDestroy()

        weatherPresenter.onDestroy()

        if (WeatherDataBase != null) {
            WeatherDataBase.destroyInstance()
            mDbWorkerThread.quit()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return applicationContext
    }

}
