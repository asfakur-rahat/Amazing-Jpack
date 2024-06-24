package com.test.ajp.screens.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.ajp.repository.WeekRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    private val _weeks : MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    val weeks : LiveData<List<String>>
        get() = _weeks
    private val repository = WeekRepository()
    fun fetchWeeks() = viewModelScope.launch {
        val response = repository.getWeeks()
        _weeks.value = response
    }
}
