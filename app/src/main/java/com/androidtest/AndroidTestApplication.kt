package com.androidtest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidTestApplication : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: AndroidTestApplication
    }


}