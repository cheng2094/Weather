package com.cursosandroidant.weather.common.entities

data class Current(
    val dt: Long? = null,
    val humidity: Int? = null,
    val temp: Double? = null,
    val weather: List<Weather> = emptyList(),
    val sunrise: Long? = null
): WeatherBase(dt, humidity, temp, weather)
