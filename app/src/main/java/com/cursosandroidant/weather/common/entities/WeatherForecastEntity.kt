package com.cursosandroidant.weather.common.entities

data class WeatherForecastEntity(
    val timezone: String? = null,
    val current: Current? = Current(),
    val hourly: List<Forecast>? = emptyList()
    )
