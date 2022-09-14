package com.cursosandroidant.weather.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosandroidant.weather.R
import com.cursosandroidant.weather.common.entities.Forecast
import com.cursosandroidant.weather.common.entities.WeatherForecastEntity
import com.cursosandroidant.weather.common.utils.CommonUtils
import com.cursosandroidant.weather.common.utils.CommonUtils.decimalFormat
import com.cursosandroidant.weather.common.utils.CommonUtils.humidityFormat
import com.cursosandroidant.weather.common.utils.Constants
import com.cursosandroidant.weather.databinding.ActivityMainBinding
import com.cursosandroidant.weather.mainModule.view.adapters.ForecastAdapter
import com.cursosandroidant.weather.mainModule.view.adapters.OnClickListener
import com.cursosandroidant.weather.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ForecastAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var result: WeatherForecastEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupObservers() {
        mainViewModel.getResult().observe(this){ resultServer ->
            result = resultServer
            binding.progress.visibility = GONE
            if (resultServer.hourly.isNullOrEmpty()){
                Snackbar.make(binding.root, R.string.main_error_empty_forecast, Snackbar.LENGTH_LONG).show()
            }else{
                setupView()
                adapter.submitList(resultServer.hourly)
            }
        }
    }

    private fun setupAdapter(){
        adapter = ForecastAdapter(this,this)
    }

    private fun setupRecyclerView(){
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupView() {
        with(binding){
            tvTimeZone.text = result.timezone?.replace("_","")
            tvCurrentTitle.visibility = VISIBLE
            tvForecastTitle.visibility = VISIBLE
            current.root.visibility = VISIBLE

            current.tvTemp.text = result.current?.temp?.decimalFormat(2)
            current.tvDt.text = CommonUtils.getHour(result.current?.dt!!)
            current.tvHumidity.text = result.current?.humidity?.humidityFormat()
            current.tvMain.text = CommonUtils.getWeatherMain(result.current?.weather)
            current.tvDescription.text = CommonUtils.getWeatherDescription(result.current?.weather)
            current.executePendingBindings()
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            binding.progress.visibility = VISIBLE
            mainViewModel.getWeatherAndForecast(9.9281, -84.0907,
                Constants.API_KEY, "", "metric", "en")
        }
    }

    /*
    * OnClickListener
    * */
    override fun onClick(forecast: Forecast) {
        Snackbar.make(binding.root, CommonUtils.getFullDate(result.current?.dt!!), Snackbar.LENGTH_LONG).show()
    }
}