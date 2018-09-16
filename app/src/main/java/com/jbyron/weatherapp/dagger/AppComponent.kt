package com.jbyron.weatherapp.dagger

import com.jbyron.weatherapp.config.App
import com.jbyron.weatherapp.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])

interface AppComponent {
    // App
    fun inject(target: App)

    // Activities
    fun inject(target: MainActivity)

    // Fragments
}
