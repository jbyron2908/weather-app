package com.jbyron.weatherapp.viewmodel

import com.jbyron.weatherapp.constant.NetworkState
import com.jbyron.weatherapp.model.WeatherData
import com.jbyron.weatherapp.service.ServiceFactory
import com.jbyron.weatherapp.service.WeatherService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherViewModel @Inject constructor(sf: ServiceFactory) {
    private val service = sf.create(WeatherService::class.java, 5, TimeUnit.MINUTES)

    // Subjects
    val state = BehaviorSubject.create<NetworkState>()!!
    val weather = BehaviorSubject.create<WeatherData>()!!

    /*
     * API Calls
     */
    fun getWeatherByLocationId(locationId: String): Observable<WeatherData> = service.getWeatherDataById(locationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { state.onNext(NetworkState.LOADING) }
            .doOnNext { weather.onNext(it) }
            .doOnError {
                Timber.e(it, "Error getting the weather by locationId")
                state.onNext(NetworkState.ERROR)
            }
            .doOnComplete { state.onNext(NetworkState.IDLE) }
}
