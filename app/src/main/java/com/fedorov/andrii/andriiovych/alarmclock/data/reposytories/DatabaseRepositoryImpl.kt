package com.fedorov.andrii.andriiovych.alarmclock.data.reposytories

import com.fedorov.andrii.andriiovych.alarmclock.data.database.AppDatabase
import com.fedorov.andrii.andriiovych.alarmclock.data.mappers.AlarmModelMapper
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories.DatabaseRepository
import com.fedorov.andrii.andriiovych.alarmclock.presentation.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val alarmModelMapper: AlarmModelMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    DatabaseRepository {

    override fun getAll(): Flow<List<AlarmModel>> {
        val result = database.alarmDao().getAll()
        return result.map { list ->
            list.map { alarmModelMapper.toAlarmModel(it) } }
    }

    override suspend fun insert(alarmModel: AlarmModel): Long = withContext(dispatcher) {
        val model = alarmModelMapper.toAlarmModelEntity(alarmModel)
        return@withContext database.alarmDao().insert(model)
    }

    override suspend fun update(alarmModel: AlarmModel) = withContext(dispatcher) {
        val model = alarmModelMapper.toAlarmModelEntity(alarmModel)
        database.alarmDao().update(model)
    }

    override suspend fun delete(alarmModel: AlarmModel) = withContext(dispatcher) {
        val model = alarmModelMapper.toAlarmModelEntity(alarmModel)
        database.alarmDao().delete(model)
    }
}