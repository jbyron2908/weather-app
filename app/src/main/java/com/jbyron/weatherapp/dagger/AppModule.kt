package com.jbyron.weatherapp.dagger

import android.app.Application
import android.content.Context
import com.jbyron.weatherapp.service.ServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideServiceFactory(context: Context): ServiceFactory = ServiceFactory(context)
}
