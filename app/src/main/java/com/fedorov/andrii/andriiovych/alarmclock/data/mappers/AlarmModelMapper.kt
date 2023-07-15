package com.fedorov.andrii.andriiovych.alarmclock.data.mappers

import com.fedorov.andrii.andriiovych.alarmclock.data.database.models.AlarmModelEntity
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import javax.inject.Inject

class AlarmModelMapper @Inject constructor() {
    fun toAlarmModel(alarmModelEntity: AlarmModelEntity): AlarmModel {
        return AlarmModel(
            id = alarmModelEntity.id,
            hours = alarmModelEntity.hours,
            minutes = alarmModelEntity.minutes,
            day = alarmModelEntity.day,
            month = alarmModelEntity.month,
            year = alarmModelEntity.year,
            description = alarmModelEntity.description
        )
    }

    fun toAlarmModelEntity(alarmModel: AlarmModel): AlarmModelEntity {
        return AlarmModelEntity(
            id = alarmModel.id,
            hours = alarmModel.hours,
            minutes = alarmModel.minutes,
            day = alarmModel.day,
            month = alarmModel.month,
            year = alarmModel.year,
            description = alarmModel.description
        )
    }
}