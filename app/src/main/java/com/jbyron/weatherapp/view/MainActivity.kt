package com.jbyron.weatherapp.view

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.jbyron.weatherapp.R
import com.jbyron.weatherapp.config.App
import com.jbyron.weatherapp.constant.NetworkState
import com.jbyron.weatherapp.view.base.BaseActivity
import com.jbyron.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val iconUrlBase = "https://www.metaweather.com/static/img/weather/png/64/xxx.png"

    private val optionsHashMap = hashMapOf(
            "Gothenburg" to "890869",
            "Stockholm" to "906057",
            "Mountain View" to "2455920",
            "London" to "44418",
            "New York" to "2459115",
            "Berlin" to "638242"
    )

    @Inject
    lateinit var viewModel: WeatherViewModel
    lateinit var currentOption: String

    init {
        App.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSpinner()

        bindViewEvents()

        loadData(optionsHashMap[currentOption]!!)
    }

    override fun bindViewModel() {
        disposables.addAll(
                viewModel.weather.subscribe {
                    val tomorrowWeather = it.consolidatedWeather[1]

                    tvWeather.text = tomorrowWeather.weatherStateName
                    Glide.with(this).load(getIconUrl(tomorrowWeather.weatherStateAbbr)).into(ivWeatherIcon)

                    tvTemperatureCurrent.text = getString(R.string.temperature, formatNumberToInt(tomorrowWeather.theTemp))
                    tvTemperatureMax.text = getString(R.string.temperature, formatNumberToInt(tomorrowWeather.maxTemp))
                    tvTemperatureMin.text = getString(R.string.temperature, formatNumberToInt(tomorrowWeather.minTemp))

                    tvWind.text = getString(R.string.wind, formatNumberToInt(tomorrowWeather.windSpeed), tomorrowWeather.windDirectionCompass)
                    tvAirPressure.text = getString(R.string.air_pressure, formatNumberToInt(tomorrowWeather.airPressure))
                    tvHumidity.text = getString(R.string.humidity, tomorrowWeather.humidity.toString())
                    tvVisibility.text = getString(R.string.visibility, formatNumber2Decimals(tomorrowWeather.visibility))

                    showWeather()
                },

                viewModel.state.subscribe {
                    if (it == NetworkState.ERROR) {
                        showError()
                    } else if (it == NetworkState.LOADING) {
                        showLoading()
                    }
                }
        )
    }

    /*
     * Private Methods
     */
    private fun setupSpinner() {
        val optionArrayList: ArrayList<String> = ArrayList(optionsHashMap.keys)
        optionArrayList.sort()
        currentOption = optionArrayList[0]

        msOptions.setItems(optionArrayList)
        msOptions.setOnItemSelectedListener { _, _, _, item ->
            currentOption = optionsHashMap[item]!!
            loadData(currentOption)
        }
    }

    private fun bindViewEvents() {
        disposables.addAll(
                RxView.clicks(tvError)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe { loadData(optionsHashMap[currentOption]!!) }
        )
    }

    private fun loadData(locationId: String) {
        disposables.add(
                viewModel.getWeatherByLocationId(locationId).subscribe({}, {
                    Timber.e(it, "Error getting weather data")
                })
        )
    }

    private fun getIconUrl(weatherAbr: String): String {
        return iconUrlBase.replace("xxx", weatherAbr)
    }

    private fun formatNumberToInt(number: Double): String {
        return "%.0f".format(number)
    }

    private fun formatNumber2Decimals(number: Double): String {
        return "%.2f".format(number)
    }

    private fun showWeather() {
        clWeather.visibility = View.VISIBLE
        pbLoading.visibility = View.GONE
        tvError.visibility = View.GONE
    }

    private fun showLoading() {
        clWeather.visibility = View.GONE
        pbLoading.visibility = View.VISIBLE
        tvError.visibility = View.GONE
    }

    private fun showError() {
        clWeather.visibility = View.GONE
        pbLoading.visibility = View.GONE
        tvError.visibility = View.VISIBLE
    }
}
