package com.jbyron.weatherapp.service

import com.jbyron.weatherapp.model.WeatherData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    companion object {
        internal const val BASE_URL = "https://www.metaweather.com/api/"
    }

    @GET("location/{locationId}")
    fun getWeatherDataById(@Path("locationId") language: String
    ): Observable<WeatherData>
}