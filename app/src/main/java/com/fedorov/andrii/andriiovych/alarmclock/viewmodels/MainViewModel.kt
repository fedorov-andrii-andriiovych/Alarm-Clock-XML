package com.fedorov.andrii.andriiovych.alarmclock.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fedorov.andrii.andriiovych.alarmclock.data.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.reposytories.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModelModelFactory(
    private val databaseRepository: DatabaseRepository = DatabaseRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(databaseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(private val databaseRepository: DatabaseRepository = DatabaseRepository()) :
    ViewModel() {

    init {
        insert(AlarmModel(time = "11:55", description = "Новый Будильник", isChecked = true))
    }

    fun getAll(): LiveData<List<AlarmModel>> {
        return databaseRepository.getAll()
    }

    fun insert(alarmModel: AlarmModel) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.insert(alarmModel)
    }

    fun update(alarmModel: AlarmModel) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.update(alarmModel)
    }

    fun delete(alarmModel: AlarmModel) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.delete(alarmModel)
    }

}