package com.fedorov.andrii.andriiovych.alarmclock.data.reposytories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.fedorov.andrii.andriiovych.alarmclock.data.database.AppDatabase
import com.fedorov.andrii.andriiovych.alarmclock.data.mappers.AlarmModelMapper
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories.DatabaseRepository
import com.fedorov.andrii.andriiovych.alarmclock.presentation.App
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    private val database: AppDatabase = App.instance.getDatabase(),
    private val alarmModelMapper: AlarmModelMapper = AlarmModelMapper(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    DatabaseRepository {

    override fun getAll(): LiveData<List<AlarmModel>> {
        val result = database.alarmDao().getAll()
        return result.map { list -> list.map { alarmModelMapper.toAlarmModel(it) } }
    }

    override suspend fun insert(alarmModel: AlarmModel): Long = withContext(dispatcher) {
        val model = alarmModelMapper.toAlarmModelEntity(alarmModel)
        return@withContext database.alarmDao().insert(model)
    }

    override suspend fun update(alarmModel: AlarmModel)= withContext(dispatcher) {
        val model = alarmModelMapper.toAlarmModelEntity(alarmModel)
        database.alarmDao().update(model)
    }

    override suspend fun delete(alarmModel: AlarmModel)= withContext(dispatcher) {
        val model = alarmModelMapper.toAlarmModelEntity(alarmModel)
        database.alarmDao().delete(model)
    }
}