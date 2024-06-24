package com.test.ajp.screens.KanjiList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.ajp.models.KanjiData
import com.test.ajp.repository.KanjiRepository
import kotlinx.coroutines.launch

class KanjiListViewModel: ViewModel() {
    private val _kanjiList: MutableLiveData<List<KanjiData>> by lazy {
        MutableLiveData<List<KanjiData>>()
    }
    val kanjiList: LiveData<List<KanjiData>>
        get() = _kanjiList

    private val repository = KanjiRepository()

    fun fetchKanjiList(week: String) = viewModelScope.launch {
        val result = repository.getKanjiList(week)
        _kanjiList.value = result
    }
}
