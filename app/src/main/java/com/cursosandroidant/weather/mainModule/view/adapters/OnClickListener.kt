package com.cursosandroidant.weather.mainModule.view.adapters

import com.cursosandroidant.weather.common.entities.Forecast

interface OnClickListener {
    fun onClick(forecast: Forecast)
}