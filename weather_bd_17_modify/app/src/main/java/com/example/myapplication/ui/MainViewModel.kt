package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Constants.TIMBER_TAG
import com.example.myapplication.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.Weather
import com.example.myapplication.domain.WeatherFrom
import com.example.myapplication.ui.dataclass.CityUI
import com.example.myapplication.ui.dataclass.WeatherUI

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _weatherList = MutableLiveData<List<WeatherUI>>()
    val weatherList: LiveData<List<WeatherUI>> = _weatherList
    private val _city = MutableLiveData<CityUI>()
    val city: LiveData<CityUI> = _city
    private val _errorNetwork = MutableLiveData<Event<Unit>>()
    val errorNetwork: LiveData<Event<Unit>> = _errorNetwork

    fun loadFromNetwork(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val weather = repository.getWeather(lat, lon)) {
                    is WeatherFrom.DataFromDatabase -> {
                        _loadFromDatabase(weather)
                    }
                    is WeatherFrom.DataFromNetwork -> {
                        _loadFromNetwork(weather)
                    }
                }
            } catch (exception: Exception) {
                Timber.tag(TIMBER_TAG).e(exception)
            }
        }
    }

    private fun _loadFromNetwork(weather: WeatherFrom.DataFromNetwork) {
        val weathers = weather.weatherData.weatherList.toUI()
        val city = weather.weatherData.city.toUI()
        _weatherList.postValue(weathers)
        _city.postValue(city)
    }

    private fun _loadFromDatabase(weather: WeatherFrom.DataFromDatabase) {
        _errorNetwork.postValue(Event(Unit))
        val weathers = weather.weatherData.weatherList.toUI()
        val city = weather.weatherData.city.toUI()
        _weatherList.postValue(weathers)
        _city.postValue(city)
    }
}