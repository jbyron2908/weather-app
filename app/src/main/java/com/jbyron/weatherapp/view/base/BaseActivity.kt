package com.jbyron.weatherapp.view.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {
    internal val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding the views to the view model
        bindViewModel()
    }

    internal open fun bindViewModel() {}

    override fun onDestroy() {
        super.onDestroy()

        // Make sure we dispose any pending observable
        disposables.dispose()
    }
}