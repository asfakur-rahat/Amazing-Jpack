package com.test.ajp.screens.Kanji

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.ajp.models.KanjiData
import com.test.ajp.repository.KanjiRepository
import kotlinx.coroutines.launch

class KanjiViewModel: ViewModel() {
    private val repository = KanjiRepository()
    private val _kanjiDetails: MutableLiveData<KanjiData> by lazy {
        MutableLiveData<KanjiData>()
    }
    val kanjiDetails: LiveData<KanjiData>
        get() = _kanjiDetails

    fun fetchKanjiDetails(week: String, id: String) = viewModelScope.launch {
        val response = repository.fetchKanjiDetails(week,id)
        _kanjiDetails.value = response
    }
}