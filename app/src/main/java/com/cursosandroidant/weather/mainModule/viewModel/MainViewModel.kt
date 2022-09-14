package com.cursosandroidant.weather.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosandroidant.weather.R
import com.cursosandroidant.weather.common.entities.WeatherForecastEntity
import com.cursosandroidant.weather.mainModule.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val repository = MainRepository()

    private val result = MutableLiveData<WeatherForecastEntity>()
    fun getResult(): LiveData<WeatherForecastEntity> = result


    suspend fun getWeatherAndForecast(lat: Double, lon: Double, appId: String, exclude: String, units: String,
                                      lang: String){
        viewModelScope.launch {
            try {
                val resultServer = repository.getWeatherAndForecast(lat, lon, appId, exclude, units, lang)
                result.postValue(resultServer)

            } catch (e: Exception) {
                result.postValue(WeatherForecastEntity())
            } finally {
            }
        }
    }
}