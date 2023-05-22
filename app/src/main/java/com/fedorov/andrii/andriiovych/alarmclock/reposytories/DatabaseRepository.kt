package com.fedorov.andrii.andriiovych.alarmclock.reposytories

import androidx.lifecycle.LiveData
import com.fedorov.andrii.andriiovych.alarmclock.App
import com.fedorov.andrii.andriiovych.alarmclock.data.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.data.AppDatabase

class DatabaseRepository(val database: AppDatabase = App.instance.getDatabase()) {

    fun getAll(): LiveData<List<AlarmModel>> {
        return database.alarmDao().getAll()
    }

    suspend fun insert(alarmModel: AlarmModel):Long {
      return  database.alarmDao().insert(alarmModel)
    }

    suspend fun update(alarmModel: AlarmModel) {
        database.alarmDao().update(alarmModel)
    }

    suspend fun delete(alarmModel: AlarmModel) {
        database.alarmDao().delete(alarmModel)
    }
}