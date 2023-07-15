package com.fedorov.andrii.andriiovych.alarmclock.domain.usecases

import androidx.lifecycle.LiveData
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DatabaseUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {


    fun getAll(): Flow<List<AlarmModel>> {
        return databaseRepository.getAll()
    }

    suspend fun insert(alarmModel: AlarmModel): Long {
        return databaseRepository.insert(alarmModel)

    }

    suspend fun update(alarmModel: AlarmModel) {
        databaseRepository.update(alarmModel)
    }

    suspend fun delete(alarmModel: AlarmModel) {
        databaseRepository.delete(alarmModel)
    }
}