package com.fedorov.andrii.andriiovych.alarmclock.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fedorov.andrii.andriiovych.alarmclock.data.database.models.AlarmModelEntity


@Database(entities = [AlarmModelEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}