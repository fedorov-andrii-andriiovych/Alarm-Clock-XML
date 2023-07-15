package com.fedorov.andrii.andriiovych.alarmclock.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fedorov.andrii.andriiovych.alarmclock.data.database.models.AlarmModelEntity

@Dao
interface AlarmDao {

    @Query("SELECT * FROM AlarmModelEntity")
    fun getAll(): LiveData<List<AlarmModelEntity>>

    @Insert
    suspend fun insert(alarmModel: AlarmModelEntity): Long

    @Update
    suspend fun update(alarmModel: AlarmModelEntity)

    @Delete
    suspend fun delete(alarmModel: AlarmModelEntity)
}