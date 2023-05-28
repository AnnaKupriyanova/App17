package com.example.myapplication.ui

import android.Manifest
import android.os.Bundle
import com.example.myapplication.domain.Weather
import androidx.activity.viewModels
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.App.Companion.repository
import com.example.myapplication.Constants
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.dataclass.WeatherUI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.logging.Handler


class MainActivity : AppCompatActivity() {
    private val weatherAdapter = WeatherAdapter {
        sendWeather(it)
    }
    private val weatherModel: MainViewModel by viewModels { WeatherFactory(repository) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var starting = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocation()
        recycleViewInit()
        observeModel()
    }

    private fun getLocation() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                Snackbar
                    .make(binding.root, "Location not accessed", Snackbar.LENGTH_LONG)
                    .show()
            }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                weatherModel.loadFromNetwork(it.latitude.toString(), it.longitude.toString())
            }
    }

    private fun observeModel() {
        observeWeatherModel()
        observeErrorEvent()
    }

    private fun observeWeatherModel() {
        weatherModel.weatherList.observe(this) {
            weatherAdapter.submitList(it)
        }
    }

    private fun observeErrorEvent() {
        weatherModel.errorNetwork.observe(this) {
            it.getContentIfNotHandled()?.let {
                Snackbar
                    .make(binding.root, "Server error", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun recycleViewInit() {
        binding.rvWeather.adapter = weatherAdapter
        binding.rvWeather.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun sendWeather(weather: WeatherUI) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${Constants.API_CITY} ${weather.dtTxt} ${weather.temp}")
            putExtra(Intent.EXTRA_SUBJECT, weatherModel.city.value?.cityName)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Send text")
        startActivity(shareIntent)
    }
}
