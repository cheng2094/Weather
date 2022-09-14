package com.cursosandroidant.weather.common.entities

open class WeatherBase(
    dt: Long? = null,
    humidity: Int? = null,
    temp: Double? = null,
    weather: List<Weather> = emptyList()
)