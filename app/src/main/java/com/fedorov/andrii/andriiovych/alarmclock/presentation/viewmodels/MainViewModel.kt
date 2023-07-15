package com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels

import androidx.lifecycle.*
import com.fedorov.andrii.andriiovych.alarmclock.data.reposytories.DatabaseRepositoryImpl
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.domain.reposytories.DatabaseRepository
import com.fedorov.andrii.andriiovych.alarmclock.domain.usecases.DatabaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModelModelFactory(
    private val databaseUseCase: DatabaseUseCase = DatabaseUseCase()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(databaseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(private val databaseUseCase: DatabaseUseCase = DatabaseUseCase()) :
    ViewModel() {

    private var _id = MutableLiveData<Long>()
    val alarmId: LiveData<Long> = _id

    fun getAll(): LiveData<List<AlarmModel>> {
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