package com.example.opscpoe.model

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber

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

        // Initialize ViewPump for custom fonts
        ViewPump.init(
            ViewPump.builder()
                .build()
        )

        // Initialize Timber for logging in Debug builds
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Other global initializations can be done here
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // Manage your application's memory based on the system's current memory usage
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                // Clean up resources related to UI components
                Timber.i("System is running low on memory: UI hidden")
            }
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                // Perform additional memory clean-up
                Timber.i("Memory clean-up at level: $level")
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle changes in configurations, such as locale changes or screen rotations
        Timber.i("Configuration changed: ${newConfig.toString()}")
    }
}
