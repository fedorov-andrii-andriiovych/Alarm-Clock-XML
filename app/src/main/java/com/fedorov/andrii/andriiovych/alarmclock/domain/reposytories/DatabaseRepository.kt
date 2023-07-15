package com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories

import androidx.lifecycle.LiveData
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    fun getAll(): Flow<List<AlarmModel>>

    suspend fun insert(alarmModel: AlarmModel): Long

    suspend fun update(alarmModel: AlarmModel)

    suspend fun delete(alarmModel: AlarmModel)
}