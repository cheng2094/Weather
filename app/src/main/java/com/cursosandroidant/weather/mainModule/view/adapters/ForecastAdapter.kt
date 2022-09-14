package com.cursosandroidant.weather.mainModule.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cursosandroidant.weather.R
import com.cursosandroidant.weather.common.entities.Forecast
import com.cursosandroidant.weather.common.utils.CommonUtils.decimalFormat
import com.cursosandroidant.weather.common.utils.CommonUtils.humidityFormat
import com.cursosandroidant.weather.common.utils.CommonUtils.getFullDate
import com.cursosandroidant.weather.common.utils.CommonUtils.getWeatherDescription
import com.cursosandroidant.weather.common.utils.CommonUtils.getWeatherIcon
import com.cursosandroidant.weather.common.utils.CommonUtils.getWeatherMain
import com.cursosandroidant.weather.common.utils.Constants
import com.cursosandroidant.weather.databinding.ItemWeatherBinding

class ForecastAdapter(private val listener: OnClickListener, var context: Context) :
    ListAdapter<Forecast, RecyclerView.ViewHolder>(ForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent,
            false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecast = getItem(position)

        with(holder as ViewHolder){
            holder.binding?.tvTemp?.setText(forecast.temp.decimalFormat(2))
            holder.binding?.tvDt?.setText(getFullDate(forecast.dt))
            holder.binding?.tvHumidity?.setText(forecast.humidity.humidityFormat())
            holder.binding?.tvPop?.setText(forecast.pop.toString())
            holder.binding?.tvMain?.setText(getWeatherMain(forecast.weather))
            holder.binding?.tvDescription?.setText(getWeatherDescription(forecast.weather))
            holder.binding?.executePendingBindings()
            Glide.with(context)
                .load(Constants.ICON_URL + getWeatherIcon(forecast.weather) + Constants.ICON_URL_EXTENSION)
                .into(holder.binding?.imIcon)


            setListener(forecast)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<ItemWeatherBinding>(view)

        fun setListener(forecast: Forecast){
            binding?.root?.setOnClickListener {
                listener.onClick(forecast)
            }
        }
    }

    class ForecastDiffCallback : DiffUtil.ItemCallback<Forecast>(){
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean = oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean = oldItem == newItem
    }
}