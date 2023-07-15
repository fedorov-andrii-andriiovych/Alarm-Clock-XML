package com.fedorov.andrii.andriiovych.alarmclock.presentation

import android.app.Application
import androidx.room.Room
import com.fedorov.andrii.andriiovych.alarmclock.data.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object{
        lateinit var instance: App
    }

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database").fallbackToDestructiveMigration()
            .build()
    }
    fun getDatabase(): AppDatabase {
        return database
    }
}