package com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories

import androidx.lifecycle.LiveData
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel

interface DatabaseRepository {

    fun getAll(): LiveData<List<AlarmModel>>

    suspend fun insert(alarmModel: AlarmModel): Long

    suspend fun update(alarmModel: AlarmModel)

    suspend fun delete(alarmModel: AlarmModel)
}