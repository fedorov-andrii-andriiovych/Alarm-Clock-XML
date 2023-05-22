package com.fedorov.andrii.andriiovych.alarmclock.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {

    @Query("SELECT * FROM AlarmModel")
    fun getAll(): LiveData<List<AlarmModel>>

    @Insert
    suspend fun insert(alarmModel: AlarmModel) : Long

    @Update
    suspend fun update(alarmModel: AlarmModel)

    @Delete
    suspend fun delete(alarmModel: AlarmModel)
}