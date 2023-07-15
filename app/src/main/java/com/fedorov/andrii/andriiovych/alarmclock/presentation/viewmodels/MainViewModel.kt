package com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels

import androidx.lifecycle.*
import com.fedorov.andrii.andriiovych.alarmclock.data.reposytories.DatabaseRepositoryImpl
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories.DatabaseRepository
import com.fedorov.andrii.andriiovych.alarmclock.domain.usecases.DatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val databaseUseCase: DatabaseUseCase) :
    ViewModel() {

    private var _id = MutableLiveData<Long>()
    val alarmId: LiveData<Long> = _id

    fun getAll(): Flow<List<AlarmModel>> {
        return databaseUseCase.getAll()
    }

    fun insert(alarmModel: AlarmModel) = viewModelScope.launch {
        val id = databaseUseCase.insert(alarmModel)
        _id.postValue(id)
    }

    fun update(alarmModel: AlarmModel) = viewModelScope.launch {
        databaseUseCase.update(alarmModel)
    }

    fun delete(alarmModel: AlarmModel) = viewModelScope.launch {
        databaseUseCase.delete(alarmModel)
    }

}