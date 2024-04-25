package com.example.opscpoe.database

import android.content.Context
import androidx.room.Room.databaseBuilder


class DatabaseClient private constructor(private val mCtx: Context) {
    //our app database object
    val appDatabase: AppDatabase

    init {
        appDatabase = databaseBuilder(mCtx, AppDatabase::class.java, "Task.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        private var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }
    }
}