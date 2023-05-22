package com.fedorov.andrii.andriiovych.alarmclock.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [AlarmModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}