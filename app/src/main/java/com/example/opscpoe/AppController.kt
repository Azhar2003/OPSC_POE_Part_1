package com.example.opscpoe

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

class AppController : Application(), ComponentCallbacks2 {

    companion object {
        private lateinit var mInstance: AppController

        @JvmStatic
        fun getInstance(): AppController = mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        ViewPump.init(
            ViewPump.builder()
                .build()
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}
