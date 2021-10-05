package com.davydikes.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.davydikes.weatherapp.databinding.ActivityMainBinding
import com.davydikes.weatherapp.support.SupportActivityInset
import com.davydikes.weatherapp.support.setVerticalMargin
import com.davydikes.weatherapp.support.setWindowTransparency
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class MainActivity : SupportActivityInset<ActivityMainBinding>(), MultiplePermissionsListener {

    override lateinit var viewBinding: ActivityMainBinding
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowTransparency(this)


        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewModel.networkConnectionLiveData.observe(this, { isConnected ->
            if (isConnected) {
                viewBinding.layoutDisconnected.visibility = View.GONE
            } else {
                viewBinding.layoutDisconnected.visibility = View.VISIBLE
            }
        })
        Dexter.withContext(applicationContext).withPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).withListener(this).check()

        val navController: NavController = navHostFragment.navController
        viewBinding.bottomNavigationView.setupWithNavController(navController)

    }


    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if (report!!.areAllPermissionsGranted()) {
            buildLocationRequest()
            buildLocationCallBack()

            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(applicationContext)
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()!!
            )
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
    ) {
        Toast.makeText(
            this,
            "Permission Denied",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                super.onLocationResult(locationResult)
                viewModel.saveLocation(locationResult.lastLocation)
            }
        }
    }


    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10.0f
    }

    override fun getActiveFragment(): Fragment? {
        return navHostFragment.childFragmentManager.fragments[0]
    }

    override fun insetsChanged(statusBarSize: Int, navigationBarSize: Int, hasKeyboard: Boolean) {
        super.insetsChanged(statusBarSize, navigationBarSize, hasKeyboard)
        viewBinding.bottomNavigationView.setVerticalMargin(marginBottom = navigationBarSize)

    }

}