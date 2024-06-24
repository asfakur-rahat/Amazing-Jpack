package com.test.ajp.screens.Kanji.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MediaViewModel: ViewModel() {
    private val _isPlaying : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isPlaying: LiveData<Boolean>
        get() = _isPlaying

    fun play() {
        _isPlaying.value = true
    }

    fun pause() {
        _isPlaying.value = false
    }

    fun onCompletion() {
        _isPlaying.value = false
    }
}