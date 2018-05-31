package com.radwaelsahn.parenthq.ui.weather

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.multidex.MultiDex
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.radwaelsahn.parenthq.BuildConfig
import com.radwaelsahn.parenthq.utils.REQUEST_PERMISSIONS_REQUEST_CODE

class LocationManager(var weatherPresenter: WeatherPresenter, var applicationContext: Context) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location


    val TAG: String = "location"


    init {
        getLocation()
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(applicationContext)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    applicationContext.startActivity(myIntent)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        mLocationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun onStart() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    fun onStop() {

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    private fun getLocation() {
        MultiDex.install(applicationContext)

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = applicationContext.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager

        checkLocation()
    }

    override fun onConnectionSuspended(p0: Int) {

        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("a", "onConnectionFailed")
        weatherPresenter.getForecast("London, UK", 5)
    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onConnected(p0: Bundle?) {
        Log.i("a", "onConnected")
        //getLastLocation()
        if (!checkPermissions())
            requestPermissions()

    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(applicationContext as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(applicationContext as Activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        // Provide an additional rationale to the user. This would happen if the user denied the // request previously, but didn’t check the "Don’t ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            // Request permission
            startLocationPermissionRequest()

        } else {
            Log.i(TAG, "Requesting permission")

            startLocationPermissionRequest()
        }
    }


    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("a", "here1")
            weatherPresenter.getForecast("London, UK", 5)
            return;
        }


        var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener({ location ->
                    // Got last known location. In some rare situations this can be null.
                    Log.i("a", "here2 location found")
                    if (location != null) {
                        var city: String = weatherPresenter.getAddress(location, applicationContext)
                        weatherPresenter.getForecast(city, 5)
                    }
                })
    }
}
