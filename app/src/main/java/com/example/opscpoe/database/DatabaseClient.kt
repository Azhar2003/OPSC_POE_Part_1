package com.example.opscpoe.database

import android.content.Context
import androidx.room.Room

object DatabaseClient {
    private var mInstance: DatabaseClient? = null

    // Our app database object
    lateinit var appDatabase: AppDatabase

    fun getInstance(context: Context): DatabaseClient {
        if (mInstance == null) {
            synchronized(this) {
                mInstance = DatabaseClient
                mInstance!!.initialize(context)
            }
        }
        return mInstance!!
    }

    private fun initialize(context: Context) {
        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Task.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getAppDatabase(): AppDatabase {
        return appDatabase
    }
}
